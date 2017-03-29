package org.bakesale.addresses;

import static org.junit.Assert.*;

import java.util.List;

import org.bakesale.addresses.implementation.AddressDatabase;
import org.bakesale.addresses.implementation.AddressEntry;
import org.junit.Test;

public class TestAddressDatabase {

	@Test 
	public void testAddressDatabaseGetAllAddresses() throws Exception
	{
			AddressDatabase database = AddressDatabase.getInstance();
			List<AddressEntry> something = database.getAllAddresses();
			assertTrue(something != null);
	}


}
