package de.RealEstateService.realEstate;

import java.util.Date;

import javax.persistence.Column;
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
	@Column(length=2000)
	private String description;
	private String image;
	private String link;
	private Integer price;
	private Boolean commission;
	private Date availabilityDate;
	@Enumerated(EnumType.ORDINAL)
	private PurchaseType purchaseType;
	private Date publicationDate;
	private String street;
	private String city;
	private String postCode;
	private Integer houseNumber;
	private String country;
	@Enumerated(EnumType.ORDINAL)
	private RealEstateType realEstateType;	
	
	// residential real estate
	private Float room;
	private Short livingArea;
	// ordinal means that the number will be persist in the database
	@Enumerated(EnumType.ORDINAL)
	private HeaterType heater;
	private Boolean energyCertificate;
	private Float energyConsumption;
	private Float additionalCosts;
	private Boolean balcony;
	private Boolean terrace;
	private Boolean garden;
	private Boolean garage;
	private Short constructionYear;
	
	// house
	private Short landArea;	
	

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean isCommission() {
		return commission;
	}

	public void setCommission(Boolean commission) {
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

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public RealEstateType getRealEstateType() {
		return realEstateType;
	}

	public void setRealEstateType(RealEstateType realEstateType) {
		this.realEstateType = realEstateType;
	}	
	
	public Float getRoom() {
		return room;
	}

	public void setRoom(Float room) {
		this.room = room;
	}

	public Short getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(Short livingArea) {
		this.livingArea = livingArea;
	}

	public HeaterType getHeater() {
		return heater;
	}

	public void setHeater(HeaterType heater) {
		this.heater = heater;
	}

	public Boolean isEnergyCertificate() {
		return energyCertificate;
	}

	public void setEnergyCertificate(Boolean energyCertificate) {
		this.energyCertificate = energyCertificate;
	}

	public Float getEnergyConsumption() {
		return energyConsumption;
	}

	public void setEnergyConsumption(Float energyConsumption) {
		this.energyConsumption = energyConsumption;
	}

	public Float getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(Float additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public Boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(Boolean balcony) {
		this.balcony = balcony;
	}

	public Boolean isTerrace() {
		return terrace;
	}

	public void setTerrace(Boolean terrace) {
		this.terrace = terrace;
	}

	public Boolean isGarden() {
		return garden;
	}

	public void setGarden(Boolean garden) {
		this.garden = garden;
	}

	public Boolean isGarage() {
		return garage;
	}

	public void setGarage(Boolean garage) {
		this.garage = garage;
	}

	public Short getConstructionYear() {
		return constructionYear;
	}

	public void setConstructionYear(Short constructionYear) {
		this.constructionYear = constructionYear;
	}
	
	public Short getLandArea() {
		return landArea;
	}

	public void setLandArea(Short landArea) {
		this.landArea = landArea;
	}	
}