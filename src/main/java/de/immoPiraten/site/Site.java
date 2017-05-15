package de.immoPiraten.site;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Site {
	
	@Id
	@GeneratedValue
	private int id;
	private String street;
	private String city;
	private String postCode;
	private String houseNumber;
	private String country;
	
	public Site(String street, String houseNumber, String postCode, String city, String country) {
		super();
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.country = country;
	}

	public Site(String country)
	{
		super();
		this.country = country;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}