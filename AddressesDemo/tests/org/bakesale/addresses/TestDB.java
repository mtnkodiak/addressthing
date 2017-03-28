package org.bakesale.addresses;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bakesale.addresses.implementation.DbConnectionInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDB {

	Connection c;
	
	@Before
	public void setup() {
		
	}

	@After
	public void teardown() throws SQLException
	{
		//c.close();
	}
	
	@Test
	public void testConnection() {
			
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(DbConnectionInfo.HOST + DbConnectionInfo.DB_NAME, DbConnectionInfo.USERNAME,
					DbConnectionInfo.PASSWORD);

		} catch (SQLException | ClassNotFoundException e) {
			fail("failed to connect to postgreSQL: " + e.getMessage());
		}

			assertTrue("connection failed:", c != null);
			
		
	}

	@Test 
	public void testGetAddrTable()
	{
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(DbConnectionInfo.HOST + DbConnectionInfo.DB_NAME, DbConnectionInfo.USERNAME,
					DbConnectionInfo.PASSWORD);

		} catch (SQLException | ClassNotFoundException e) {
			fail("failed to connect to postgreSQL: " + e.getMessage());
		}

		try {
	         Statement stmt = c.createStatement();
	         String sql = "SELECT * FROM ADDRESSES_DATA;";
	         
	         ResultSet rs = stmt.executeQuery(sql);
	         
	         assertTrue(rs != null);
		}
		catch (SQLException sqlex)
		{
			fail("failed to get address table from postgreSQL: " + sqlex.getMessage());
		}
	}
	
}
