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
	private HeatingType heating;
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

	private Short floor;
	private Short numberOfFloors;
	private Short numberOfBedRooms;
	private Short numberOfBathRooms;
	private Short numberOfParkingSpaces;
	private Float heatingCosts;
	private Boolean heatingCostsInAdditionalCosts;
	private String deposit;
	private Float thermalCharacteristic;
	private String locationNote;
	private String otherNote;
	private String furnishingNote;	
	private String energyEfficiencyClass;
	
	@Enumerated(EnumType.ORDINAL)
	private ConditionType objectstate;
	@Enumerated(EnumType.ORDINAL)
	private InteriorQualityType interiorQuality;
	@Enumerated(EnumType.ORDINAL)
	private BuildingEnergyRatingType buildingEnergyRating;
	@Enumerated(EnumType.ORDINAL)
	private FiringType firing;
	@Enumerated(EnumType.ORDINAL)
	private ResidentialRealEstateType residentialRealEstate;
	
	// contact
	private String contactFirstName;
	private String contactLastName;
	private TitleType contactTitle;
	private String contactTelephone;
	private String contactEmail;
	private String contactMobile;
	private String contactCompany;	
	
	// site
	private String siteStreet;
	private String siteCity;
	private String sitePostCode;
	private String siteHouseNumber;
	private String siteCountry;	
	
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

	public HeatingType getHeating() {
		return heating;
	}

	public void setHeating(HeatingType heating) {
		this.heating = heating;
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
	
	public Short getFloor() {
		return floor;
	}

	public void setFloor(Short floor) {
		this.floor = floor;
	}

	public Short getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(Short numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public Short getNumberOfBedRooms() {
		return numberOfBedRooms;
	}

	public void setNumberOfBedRooms(Short numberOfBedRooms) {
		this.numberOfBedRooms = numberOfBedRooms;
	}

	public Short getNumberOfBathRooms() {
		return numberOfBathRooms;
	}

	public void setNumberOfBathRooms(Short numberOfBathRooms) {
		this.numberOfBathRooms = numberOfBathRooms;
	}

	public Short getNumberOfParkingSpaces() {
		return numberOfParkingSpaces;
	}

	public void setNumberOfParkingSpaces(Short numberOfParkingSpaces) {
		this.numberOfParkingSpaces = numberOfParkingSpaces;
	}

	public Float getHeatingCosts() {
		return heatingCosts;
	}

	public void setHeatingCosts(Float heatingCosts) {
		this.heatingCosts = heatingCosts;
	}

	public Boolean getHeatingCostsInAdditionalCosts() {
		return heatingCostsInAdditionalCosts;
	}

	public void setHeatingCostsInAdditionalCosts(Boolean heatingCostsInAdditionalCosts) {
		this.heatingCostsInAdditionalCosts = heatingCostsInAdditionalCosts;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public Float getThermalCharacteristic() {
		return thermalCharacteristic;
	}

	public void setThermalCharacteristic(Float thermalCharacteristic) {
		this.thermalCharacteristic = thermalCharacteristic;
	}

	public String getLocationNote() {
		return locationNote;
	}

	public void setLocationNote(String locationNote) {
		this.locationNote = locationNote;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public String getFurnishingNote() {
		return furnishingNote;
	}

	public void setFurnishingNote(String furnishingNote) {
		this.furnishingNote = furnishingNote;
	}

	public String getEnergyEfficiencyClass() {
		return energyEfficiencyClass;
	}

	public void setEnergyEfficiencyClass(String energyEfficiencyClass) {
		this.energyEfficiencyClass = energyEfficiencyClass;
	}

	public ConditionType getObjectstate() {
		return objectstate;
	}

	public void setObjectstate(ConditionType objectstate) {
		this.objectstate = objectstate;
	}

	public InteriorQualityType getInteriorQuality() {
		return interiorQuality;
	}

	public void setInteriorQuality(InteriorQualityType interiorQuality) {
		this.interiorQuality = interiorQuality;
	}

	public BuildingEnergyRatingType getBuildingEnergyRating() {
		return buildingEnergyRating;
	}

	public void setBuildingEnergyRating(BuildingEnergyRatingType buildingEnergyRating) {
		this.buildingEnergyRating = buildingEnergyRating;
	}

	public FiringType getFiring() {
		return firing;
	}

	public void setFiring(FiringType firing) {
		this.firing = firing;
	}
	
	public ResidentialRealEstateType getResidentialRealEstate() {
		return residentialRealEstate;
	}

	public void setResidentialRealEstate(ResidentialRealEstateType residentialRealEstate) {
		this.residentialRealEstate = residentialRealEstate;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public TitleType getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(TitleType contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	public String getSiteStreet() {
		return siteStreet;
	}

	public void setSiteStreet(String siteStreet) {
		this.siteStreet = siteStreet;
	}

	public String getSiteCity() {
		return siteCity;
	}

	public void setSiteCity(String siteCity) {
		this.siteCity = siteCity;
	}

	public String getSitePostCode() {
		return sitePostCode;
	}

	public void setSitePostCode(String sitePostCode) {
		this.sitePostCode = sitePostCode;
	}

	public String getSiteHouseNumber() {
		return siteHouseNumber;
	}

	public void setSiteHouseNumber(String siteHouseNumber) {
		this.siteHouseNumber = siteHouseNumber;
	}

	public String getSiteCountry() {
		return siteCountry;
	}

	public void setSiteCountry(String siteCountry) {
		this.siteCountry = siteCountry;
	}
}