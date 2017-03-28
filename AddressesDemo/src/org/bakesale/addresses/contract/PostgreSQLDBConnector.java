package org.bakesale.addresses.contract;

	import java.net.URI;
	import java.sql.ResultSet;

	public interface PostgreSQLDBConnector 
	{
		void setConnectionInfo(String host, int port, String user, String password);
		void setDbName(String newName) throws Exception;
		boolean setConnectionInfo(URI connectionUri) throws Exception;
		ResultSet doSelect(String selectSql) throws Exception;
		void doUpdate(String updateSql) throws Exception;
		void doInsert(String insertSql) throws Exception;
		void doDelete(String deleteSql) throws Exception;

	}
