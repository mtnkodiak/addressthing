package org.bakesale.addresses.implementation;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bakesale.addresses.contract.PostgreSQLDBConnector;
import org.bakesale.addresses.contract.logger.CJLogger;

/**
 *  Class implements the interface to the JDBC connector in the
 *  library/extern .jar file.
 *  
 *  This class is designed to be able to receive SQL command strings
 *  and forward the requested action to the database instance and pass
 *  back any results.
 *  
 *  STATIC connection member design notes
 *     The current implementation of the PSQL DB Connection class is "unclose-able"
 *     meaning that even proper .close()'ing of the ResultSet, the Staement, and
 *     the Connection do not result in a closed socket to the server.  We were able to
 *     blow up the OS after 40K or so open connections to the srever; the actual
 *     error message was "TCP connection refused".
 *     
 *     So, since the DB connection does not close using traditional/accepted means,
 *     we are going to the other extreme and re-using the same DB connection for
 *     all statements.  The only way to do this without impacting a ton of the current
 *     HdsVP datamanager code is to make the connection static so that all instances of
 *     the PostgreSQLDBConnectionImpl use the same object.
 *     
 *     The static DB object is serialized so that statement creation is assured to 
 *     happen serially.
 */
public class PostgreSQLDBConnectorImpl implements PostgreSQLDBConnector 
{
	private String  host = DbConnectionInfo.HOST;
	private int port = DbConnectionInfo.PORT;
	private String user = DbConnectionInfo.USERNAME;
	private String password = DbConnectionInfo.PASSWORD;
	private String dbName = DbConnectionInfo.DB_NAME;
	private CJLogger logger;
	
	private static Object oneConnectionActionLock = new Object();
	private static Connection dbConnection = null;
	
	public PostgreSQLDBConnectorImpl(CJLogger logger) throws ClassNotFoundException
	{		
		this.logger = logger;
		
		try
		{
			Class.forName( "org.postgresql.Driver" );
		}
		catch( ClassNotFoundException exception1 )
		{
			String errorMsg = "DbConnectorPostgreSQLImpl(): initialization failure " + exception1.getMessage();
			logger.error(errorMsg);
			throw exception1;
		}
	}
	
	/* 
	 */
	@Override
	public void setConnectionInfo( String host, int port, String user, String password )
	{
		if( null!=host ) this.host = host;
		if( 0!=port ) this.port = port;
		if( null!=user ) this.user = user;
		if( null!=password ) this.password = password;
	}
	
	
	/* 
	 */
	@Override
	public void setDbName( String newName )
	{
		if( null!=newName ) this.dbName = newName;
	}
	
	/* 
	 */
	@Override
	public boolean setConnectionInfo( URI connectionUri )
	{
//		boolean uriChanged = connectionUri.equals(previousConnectionUri);
//		
//		if (uriChanged)
//		{
//	        synchronized(previousUriLock)
//	        {
//				previousConnectionUri = connectionUri;
//			}
//		}
		
		if( connectionUri.getScheme().toUpperCase().equals( "SQL" ) )
		{
			String host     = connectionUri.getHost();
			int    port     = connectionUri.getPort();
			String userinfo = connectionUri.getUserInfo();
			String dbname   = connectionUri.getPath();
			
			// Break the userinfo (if present) into username and password
			// If any part of the userinfo (or all of it) is null, this code must still
			// execute while setting user=null and password=null
			String user = null;
			String password = null;
			if( null!=userinfo )
		    {
				int userSeparator = userinfo.indexOf( '.' );
				if( userSeparator > 0 )
				{
					user = userinfo.substring( 0, userSeparator );
					password = userinfo.substring( userSeparator+1, userinfo.length() );
				}
				else
				{
					user = userinfo;
				}
		    }
			
			// remove the leading '/' from the path to get the DB name
			if( dbname.length() > 1 )
			{
				dbname = dbname.substring( 1, dbname.length());
			}
			// in case there is a trailing '/' or other path information, trim it
			int separatorIndex = dbname.indexOf( '/' );
			
			if( separatorIndex > 0 )
			{
				dbname = dbname.substring(0 ,  separatorIndex );
			}
			
			
			this.setConnectionInfo( host,  port,  user,  password );
			this.setDbName( dbname );
		}
		
		return false;
	}

	private void connect() throws Exception
	{
		if( null==dbConnection )
		{
			synchronized( oneConnectionActionLock )
			{
				String dbURL  = "jdbc:postgresql://" + host + ":" + port;
			           dbURL += "/" + this.dbName;
			 
			       
			    Connection newConnection = null;
			    try
			    {
			    	logger.information("Making a new connection to " + dbURL);
                    newConnection = DriverManager.getConnection(dbURL, this.user, this.password);
			    }
			    catch( SQLException sqlex )
			    {
			    	// Note - we don't use the postgresl reported exception message here because it
			    	// often includes the user credentials, and people at HDS dont want to see credential
			    	// information in the log.  So, we are not appending the thrown exception text onto
			    	// our log message.
			     	logger.error(sqlex);
				    throw sqlex;
			    }
			
			    dbConnection = newConnection;
			}
		}
	}
	
	/* 
	 */
	@Override
	public ResultSet doSelect( String selectSql ) throws Exception
	{
		ResultSet retVal = null;
		
		this.connect();
		try
		{
			synchronized( oneConnectionActionLock )
			{
				Statement statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    retVal = statement.executeQuery( selectSql );
			}
		}
		catch( Exception sqlex )
		{
			String message = "doSelect failure with exception message: " + sqlex.getMessage();
			logger.error(message);
			
			this.close();
			
			throw sqlex;
		}
		
		return retVal;
	}
	
	/* 
	 */
	@Override
	public void doUpdate( String updateSql ) throws Exception
	{
		this.connect();
		try
		{
			synchronized( oneConnectionActionLock )
			{
				Statement statement = dbConnection.createStatement();
				statement.executeUpdate( updateSql );
				statement.close();
			}
		}
		catch(SQLException sqlEx)
		{
			String message = 
					"doUpdate failure with exception sqlState: " + sqlEx.getSQLState() + " dbConnection " + dbConnection
					+ " " + updateSql + " message " + sqlEx.getMessage();
			logger.error(message);
			
			this.close();
			
			throw sqlEx;			
		}
		catch( Exception ex )
		{
			String message = 
					"doUpdate failure with exception message: " + ex.getMessage() + " " + updateSql;
			logger.error(message);
			
			this.close();
			
			throw ex;
		}
	}

	/*
	 */
	@Override
	public void doInsert( String insertSql )throws Exception
	{
		this.doUpdate( insertSql );
	}
	
	/*
    */
	@Override
	public void doDelete( String deleteSql ) throws Exception
	{
		this.doUpdate( deleteSql );
	}
	

	private void close()
	{
		synchronized( oneConnectionActionLock )
		{
		    if( null!=dbConnection )
		    {
		    	try
			    {
		    		logger.information("Closing database connection");
			        dbConnection.close();
			    }
			    catch( SQLException ignore )
			    {
			    	// intentionally ignore throw on close()
			    }
			
			    dbConnection = null;
		    }
		}
	}
}

