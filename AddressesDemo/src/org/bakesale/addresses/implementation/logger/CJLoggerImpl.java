package org.bakesale.addresses.implementation.logger;

import org.bakesale.addresses.contract.logger.CJLogger;

import java.io.File;
import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CJLoggerImpl implements CJLogger
{
    public static final String LOGGER_NAME         = "org.bakesale";
    private static final String LOG_FILENAME       = "addresses.log";

    private Logger logger;
    private LogRecordFactory logRecordFactory;

    public CJLoggerImpl() throws Exception
    {
        this.logRecordFactory = new LogRecordFactory();
        
        // This commands the JVM to give us a logger - it will never be null.
        // When this is the first logger of the specified name, the JVM creates
        // a logger referenced by the name, so there is no support for a "is this
        // logger already in existence query" using getLogger()
        //
        this.logger = Logger.getLogger(LOGGER_NAME);
    }

    /**
	 * Initialize, but be mindful of the reality that the Java logger object that we
	 * use is created by the JVM and is global, even across multiple Tomcat services.
	 * So, when we create a handler for logging to a file and add this handler to the
	 * Java logger, this handler is going to be present in future invocations of 
	 * Logger.initialize(), even if in a different Tomcat Service and even if 
	 * we try to skip initialization with private static boolean Logger.initialized 
	 * or similar method.
     */
    @Override
    public void initialize(String logPath) throws Exception
    {
    	// Using the raw java.util.logger (and not this class until initialization is complete)
        logger.log(Level.INFO, "initialize, path=" + logPath );

        try
        {
        	boolean alreadyConfigured = this.isLoggerAlreadyConfigured();
        	if( !alreadyConfigured )
        	{
        		this.updateConsoleHandler();
        		this.addFileHandler( logPath );
        	}
        }
        catch (Exception cause)
        {
        	// NOTE: Our logger failed to create so use the raw java.util.logger
        	logger.log(Level.SEVERE, cause.getMessage());
            
        	throw cause;
        }
    }
    
    /**
     * Private function that detects whether or not the global
     * Java logger has already been configured by the VpLoggerImpl class (this).
     * We use whether or not our custom console handler has been added to the 
     * logger as the indication of whether or not the logger is configured.
     * 
     * If the Java logger has already been configured, then the console logger
     * will be an instance of our VpHandler.
     * 
     * Note that extra work is required across service instances such as the VpWebService
     * to the VasaServiceImpl.  The VpWebService "instanceof" VpConsoleHandler
     * does not match VasaService "instanceof" VpConsoleHandler.  So even though the
     * classes are derived from the same source and .class file, they are not the
     * same from the standpoint of "instanceof".  So, instead we use a string class
     * name match to detect the presence of our console handler class.
     */
    private boolean isLoggerAlreadyConfigured()
    {
        Handler[] handlers = this.logger.getHandlers();
        boolean   hasOurConsoleHandler = false;
        
        for( Handler handler : handlers )
        {
        	// Match by class name and not by "instanceof".  The instanceof match
        	// does not work across Tomcat services as each service has a different 
        	// classloader and the instanceof directive does not match classes by
        	// different class loaders.
        	//
        	String handlerClassName = handler.getClass().getName();
        	if( handlerClassName.equals( VpConsoleHandler.class.getName() ))
        	{
        		hasOurConsoleHandler = true;
        		break;
        	}
        }
        
        return hasOurConsoleHandler;
    }
    
    /**
     * local function that will change the format of the console output to match that of
     * our log file.  
     *
     * We are also changing the output stream used for console output from
     * stderr to stdout.  For some goofy reason, Java is militant about
     * logging to stderr where the rest of humanity uses stdout until 
     * something critical happens, then it appears on stderr.
     */
    private void updateConsoleHandler()
    {
    	Logger defaultLogger = Logger.getLogger("");
        Handler[] defaultHandlers = defaultLogger.getHandlers();
        
        // Capture default handlers information
        for (Handler handler : defaultHandlers)
        {
        	if( handler instanceof ConsoleHandler )
        	{	
        		// create a console handler with the same attribs as the default,
        		// except that it will log to stdout
        		VpConsoleHandler newHandler = new VpConsoleHandler();
        		newHandler.setFormatter( handler.getFormatter() );
        		newHandler.setLevel( handler.getLevel() );
        		newHandler.setErrorManager( handler.getErrorManager() );
        		newHandler.setFilter( handler.getFilter() );
        		
        		// this closes the default Console handler, and removes it from
        		// the default logger
        		defaultLogger.removeHandler( handler );
        		
        		// now we replace it with one that uses stdout and set the formatter
        		defaultLogger.addHandler( newHandler );
        	}
        }
    }
    

    private void addFileHandler(String logPath)
    throws Exception
    {
    	// first the console handler
    	VpConsoleHandler newHandler = new VpConsoleHandler();
    	newHandler.setFormatter( new CJLogFormatter() );
    	newHandler.setLevel( Level.ALL );
    	this.logger.addHandler( newHandler );
    	this.logger.setLevel(Level.ALL);
    	this.logger.setUseParentHandlers( false );
        	
    	// then the file handler as well
    	String hdsVpLogFullPath = this.toFullLogPath( logPath );
    	File logDirectory = new File(logPath);
    	logDirectory.mkdirs();
            
    	FileHandler fileHandler = new FileHandler(hdsVpLogFullPath);
    	fileHandler.setLevel(Level.INFO);
    	fileHandler.setFormatter(new CJLogFormatter());
    	logger.addHandler(fileHandler);
    }
   
    /** 
     * private utility function that converts a path into a full/long path
     * even when the specified path is null or empty
     */
    private String toFullLogPath( String logPath )
    {
    	if (logPath == null || logPath.isEmpty())
        {
        	logPath = ".";
        }
        
        return logPath + "/" + LOG_FILENAME;
    }
   
    // VpException handling
    @Override
    public void error(Exception event)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.SEVERE, event);
        logger.log(record);
    }

    @Override
    public void warning(Exception event)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.WARNING, event);
        logger.log(record);
    }

    @Override
    public void information(Exception event)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.INFO, event);
        logger.log(record);
    }

    @Override
    public void trace(Exception event)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.FINE, event);
        logger.log(record);
    }
    
    // message handling
    @Override
    public void error(String message)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.SEVERE, message);
        logger.log(record);
    }

    @Override
    public void warning(String message)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.WARNING, message);
        logger.log(record);    
    }

    @Override
    public void information(String message)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.INFO, message);
        logger.log(record);
    }

    @Override
    public void trace(String message)
    {
        LogRecord record = logRecordFactory.createLogRecord(Level.FINE, message);
        logger.log(record);
    }
    
    public class VpConsoleHandler extends ConsoleHandler
    {
        @Override
        protected void setOutputStream(OutputStream ignoredParam) throws SecurityException
        {
            super.setOutputStream(System.out);
        }
    }
}
