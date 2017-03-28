package org.bakesale.addresses.implementation;

import java.util.ArrayList;
import java.util.List;

import org.bakesale.addresses.contract.PostgreSQLDBConnector;
import org.bakesale.addresses.contract.logger.CJLogger;
import org.bakesale.addresses.implementation.logger.CJLoggerImpl;

import com.google.gson.Gson;

/**
 * A singleton address database object, using postgreSQL as the database access
 * @author cjjohnson
 *
 */
public class AddressDatabase {
    
	private PostgreSQLDBConnector dbConnector;
	private CJLogger logger;
    private List<AddressEntry> addresses;
    
    // Singleton methods
    private static AddressDatabase dbInstance = null;
    
    protected AddressDatabase() throws Exception {
    	logger = new CJLoggerImpl();
    	logger.initialize("./log");
        addresses = new ArrayList<>();
        dbConnector = new PostgreSQLDBConnectorImpl(logger);
        dbConnector.setConnectionInfo("localhost", DbConnectionInfo.PORT, DbConnectionInfo.USERNAME, DbConnectionInfo.PASSWORD);
    }
    
    public static AddressDatabase getInstance() throws Exception {
       if(dbInstance == null) {
          dbInstance = new AddressDatabase();
       }
       
       return dbInstance;
    }
    
    
    public void addAddresses(ArrayList<AddressEntry> addresses) {
        addresses.addAll(addresses);
    }
    
    public String getAllAddresses() throws Exception {
    	String selectSql = "SELECT * FROM ADDRESSES_TABLE";
    	dbConnector.doSelect(selectSql);
        return new Gson().toJson(addresses);
    }
    
}
