package org.bakesale.addresses.implementation;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bakesale.addresses.contract.PostgreSQLDBConnector;
import org.bakesale.addresses.contract.logger.CJLogger;
import org.bakesale.addresses.implementation.logger.CJLoggerImpl;

import com.google.gson.Gson;

/**
 * A singleton address database object, using postgreSQL as the database access
 * 
 * @author cjjohnson
 *
 */
public class AddressDatabase {

	private PostgreSQLDBConnector dbConnector;
	private CJLogger logger;
	private List<AddressEntry> addresses;

	// Singleton methods
	private static AddressDatabase dbInstance = null;

	protected AddressDatabase() throws ClassNotFoundException {
		logger = new CJLoggerImpl();
		logger.initialize("./log");
		addresses = new ArrayList<>();
		dbConnector = new PostgreSQLDBConnectorImpl(logger);
		dbConnector.setConnectionInfo("localhost", DbConnectionInfo.PORT, DbConnectionInfo.USERNAME,
				DbConnectionInfo.PASSWORD);
	}

	public static AddressDatabase getInstance() throws ClassNotFoundException {
		if (dbInstance == null) {
			dbInstance = new AddressDatabase();
		}

		return dbInstance;
	}

	public void addAddresses(ArrayList<AddressEntry> addresses) {
		addresses.addAll(addresses);
	}

	public List<AddressEntry> getAllAddresses() throws Exception {
		String selectSql = "SELECT * FROM ADDRESSES_DATA";
		ResultSet resultSet = dbConnector.doSelect(selectSql);

		List<AddressEntry> entries = new ArrayList<AddressEntry>();

		while (resultSet.next()) {
			AddressEntry entry = new AddressEntry(resultSet.getInt("ID"), resultSet.getString("NAME"),
					resultSet.getString("EMAIL"), resultSet.getString("TELEPHONE"), resultSet.getString("ADDR_STREET"),
					resultSet.getString("ADDR_CITY"), resultSet.getString("ADDR_STATE"),
					resultSet.getString("ADDR_ZIP"));

			entries.add(entry);

		}

		return entries;
	}

	public boolean deleteAddress(int index) throws Exception {
		boolean success = true;

		String deleteSql = "DELETE FROM ADDRESSES_DATA WHERE id=" + index;
		dbConnector.doDelete(deleteSql);

		return success;
	}

	public AddressEntry getAddress(int index) throws Exception {

		String selectSql = "SELECT * FROM ADDRESSES_DATA WHERE id=" + index;
		ResultSet resultSet = dbConnector.doSelect(selectSql);
		resultSet.next();
		AddressEntry entry = new AddressEntry(resultSet.getInt("ID"), resultSet.getString("NAME"),
				resultSet.getString("EMAIL"), resultSet.getString("TELEPHONE"), resultSet.getString("ADDR_STREET"),
				resultSet.getString("ADDR_CITY"), resultSet.getString("ADDR_STATE"), resultSet.getString("ADDR_ZIP"));
		return entry;
	}

	public boolean updateAddress(int index, AddressEntry entry) throws Exception {
		boolean success = true;

		String updateSql = "UPDATE ADDRESSES_DATA " + "SET name = '" + entry.getName() + "', email = '"
				+ entry.getEmail() + "', telephone = '" + entry.getTelephone() + "', addr_street = '"
				+ entry.getAddrStreet() + "', addr_city = '" + entry.getAddrCity() + "', addr_state = '" + entry.getAddrState() + "', addr_zip = '" + entry.getZipcode() + "' " 
				+ " WHERE id=" + index;

		dbConnector.doUpdate(updateSql);

		return success;
	}

}
