import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bakesale.addresses.implementation.DbConnectionInfo;
import org.junit.Test;

public class TestDBConnection {

	@Test
	public void testConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection(
					DbConnectionInfo.HOST+DbConnectionInfo.DB_NAME,
					DbConnectionInfo.USERNAME,
					DbConnectionInfo.PASSWORD);
			
			assertTrue("connection failed.", c != null);
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
