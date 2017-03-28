package org.bakesale.addresses;

import static org.junit.Assert.*;

import org.bakesale.addresses.implementation.AddressDatabase;
import org.junit.Test;

public class TestDBConnector {

	@Test 
	public void testAddressDatabaseGetAllAddresses() throws Exception
	{
			AddressDatabase database = AddressDatabase.getInstance();
			String something = database.getAllAddresses();
			assertTrue(something != null);
	}


}
