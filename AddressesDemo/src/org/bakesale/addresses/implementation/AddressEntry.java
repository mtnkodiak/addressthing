package org.bakesale.addresses.implementation;

/**
 * A holder for address data
 * @author cjjohnson
 *
 */
public class AddressEntry {

	private int id;
	private String name;
	private String email;
	private String telephone;
	private String addrStreet;
	private String addrCity;
	private String addrState;
	private String zipcode;
	
	public AddressEntry(int id, String name, String email, String telephone, String addrStreet, String addrCity, String addrState, String zipcode)
	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.addrStreet = addrStreet;
		this.addrCity = addrCity;
		this.addrState = addrState;
		this.zipcode = zipcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddrStreet() {
		return addrStreet;
	}
	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getAddrState() {
		return addrState;
	}
	public void setAddrState(String addrState) {
		this.addrState = addrState;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
