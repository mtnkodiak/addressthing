import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bakesale.addresses.implementation.DbConnectionInfo;
import org.junit.Test;

public class TestDB {

	@Test
	public void testConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection(
					DbConnectionInfo.HOST+DbConnectionInfo.DB_NAME,
					DbConnectionInfo.USERNAME,
					DbConnectionInfo.PASSWORD);
			
			assertTrue("connection failed:", c != null);
			
		} catch (SQLException | ClassNotFoundException e) {
			fail("failed to connect to postgreSQL: " + e.getMessage());
		}
	}

	@Test 
	public void testGetAddrTable()
	{
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection(
					DbConnectionInfo.HOST+DbConnectionInfo.DB_NAME,
					DbConnectionInfo.USERNAME,
					DbConnectionInfo.PASSWORD);

	         Statement stmt = c.createStatement();
	         String sql = "SELECT * FROM ADDRESSES_DATA;";
	         
	         ResultSet rs = stmt.executeQuery(sql);
	         
	         assertTrue(rs != null);
		}
		catch (SQLException | ClassNotFoundException sqlex)
		{
			fail("failed to get address table from postgreSQL: " + sqlex.getMessage());
		}
	}
}
