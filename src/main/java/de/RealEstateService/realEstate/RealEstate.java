package de.RealEstateService.realEstate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RealEstate {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String description;
	private String image;
	private String link;
	private int price;
	private boolean commission;
	private Date availabilityDate;
	@Enumerated(EnumType.ORDINAL)
	private PurchaseType purchaseType;
	private Date publicationDate;
	private String street;
	private String city;
	private String postCode;
	private int houseNumber;
	private String country;
	
	// residential real estate
	private short room;
	private short livingArea;
	// ordinal means that the number will be persist in the database
	@Enumerated(EnumType.ORDINAL)
	private HeaterType heater;
	private boolean energyCertificate;
	private double energyConsumption;
	private float additionalCosts;
	private boolean balcony;
	private boolean terrace;
	private boolean garden;
	private boolean garage;
	private short constructionYear;
	
	// house
	private int landArea;	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isCommission() {
		return commission;
	}

	public void setCommission(boolean commission) {
		this.commission = commission;
	}

	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
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

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public short getRoom() {
		return room;
	}

	public void setRoom(short room) {
		this.room = room;
	}

	public short getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(short livingArea) {
		this.livingArea = livingArea;
	}

	public HeaterType getHeater() {
		return heater;
	}

	public void setHeater(HeaterType heater) {
		this.heater = heater;
	}

	public boolean isEnergyCertificate() {
		return energyCertificate;
	}

	public void setEnergyCertificate(boolean energyCertificate) {
		this.energyCertificate = energyCertificate;
	}

	public double getEnergyConsumption() {
		return energyConsumption;
	}

	public void setEnergyConsumption(double energyConsumption) {
		this.energyConsumption = energyConsumption;
	}

	public float getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(float additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isTerrace() {
		return terrace;
	}

	public void setTerrace(boolean terrace) {
		this.terrace = terrace;
	}

	public boolean isGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	public boolean isGarage() {
		return garage;
	}

	public void setGarage(boolean garage) {
		this.garage = garage;
	}

	public short getConstructionYear() {
		return constructionYear;
	}

	public void setConstructionYear(short constructionYear) {
		this.constructionYear = constructionYear;
	}
	
	public int getLandArea() {
		return landArea;
	}

	public void setLandArea(int landArea) {
		this.landArea = landArea;
	}	
}