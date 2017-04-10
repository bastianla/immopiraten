package de.immoPiraten.contact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contact {

	@Id
	@GeneratedValue
	private int id;
	private String firstName;
	private String lastName;
	private String title; // Enum?
	private String telephone;
	private String email;
	private String mobile;
	private String company;
	
	public Contact() {
	}

	public Contact(String firstName, String lastName, String title, String telephone, String email, String mobile, String company) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.telephone = telephone;
		this.email = email;
		this.mobile = mobile;
		this.company = company;
	}

	public int getId() {
		return id;
	}

	// Benötigt?
	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}	
}