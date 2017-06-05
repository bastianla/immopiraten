package de.immoPiraten.query;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import de.immoPiraten.realEstate.PurchaseType;
import de.immoPiraten.realEstate.RealEstateType;

@Entity
public class Query {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private RealEstateType realEstateType;
	private Boolean notification;
	private Date lastNotification;
	private PurchaseType purchaseType;
	private Integer priceFrom;
	private Integer priceTo;
	private Float roomsFrom;
	private Float roomsTo;
	private Short livingAreaFrom;
	private Short livingAreaTo;
	private Short landAreaFrom;
	private Short landAreaTo;
	private Short constructionYearFrom;
	private Short constructionYearTo;
	private Byte radius;
	private Boolean balcony;
	private Boolean terrace;
	private Boolean garden;
	private Boolean garage;
	private Boolean commission;
	
	public Query(){
	}
	
	public Query(String name, RealEstateType realEstateType, Boolean notification, Date lastNotification,
			PurchaseType purchaseType, Integer priceFrom, Integer priceTo, Float roomsFrom, Float roomsTo,
			Short livingAreaFrom, Short livingAreaTo, Short landAreaFrom, Short landAreaTo, Short constructionYearFrom,
			Short constructionYearTo, Byte radius, Boolean balcony, Boolean terrace, Boolean garden, Boolean garage,
			Boolean commission) {
		super();
		this.name = name;
		this.realEstateType = realEstateType;
		this.notification = notification;
		this.lastNotification = lastNotification;
		this.purchaseType = purchaseType;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.roomsFrom = roomsFrom;
		this.roomsTo = roomsTo;
		this.livingAreaFrom = livingAreaFrom;
		this.livingAreaTo = livingAreaTo;
		this.landAreaFrom = landAreaFrom;
		this.landAreaTo = landAreaTo;
		this.constructionYearFrom = constructionYearFrom;
		this.constructionYearTo = constructionYearTo;
		this.radius = radius;
		this.balcony = balcony;
		this.terrace = terrace;
		this.garden = garden;
		this.garage = garage;
		this.commission = commission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RealEstateType getRealEstateType() {
		return realEstateType;
	}

	public void setRealEstateType(RealEstateType realEstateType) {
		this.realEstateType = realEstateType;
	}

	public Boolean isNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}

	public Date getLastNotification() {
		return lastNotification;
	}

	public void setLastNotification(Date lastNotification) {
		this.lastNotification = lastNotification;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Integer getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(Integer priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Integer getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Integer priceTo) {
		this.priceTo = priceTo;
	}

	public Float getRoomsFrom() {
		return roomsFrom;
	}

	public void setRoomsFrom(Float roomsFrom) {
		this.roomsFrom = roomsFrom;
	}

	public Float getRoomsTo() {
		return roomsTo;
	}

	public void setRoomsTo(Float roomsTo) {
		this.roomsTo = roomsTo;
	}

	public Short getLivingAreaFrom() {
		return livingAreaFrom;
	}

	public void setLivingAreaFrom(Short livingAreaFrom) {
		this.livingAreaFrom = livingAreaFrom;
	}

	public Short getLivingAreaTo() {
		return livingAreaTo;
	}

	public void setLivingAreaTo(Short livingAreaTo) {
		this.livingAreaTo = livingAreaTo;
	}

	public Short getLandAreaFrom() {
		return landAreaFrom;
	}

	public void setLandAreaFrom(Short landAreaFrom) {
		this.landAreaFrom = landAreaFrom;
	}

	public Short getLandAreaTo() {
		return landAreaTo;
	}

	public void setLandAreaTo(Short landAreaTo) {
		this.landAreaTo = landAreaTo;
	}

	public Short getConstructionYearFrom() {
		return constructionYearFrom;
	}

	public void setConstructionYearFrom(Short constructionYearFrom) {
		this.constructionYearFrom = constructionYearFrom;
	}

	public Short getConstructionYearTo() {
		return constructionYearTo;
	}

	public void setConstructionYearTo(Short constructionYearTo) {
		this.constructionYearTo = constructionYearTo;
	}

	public Byte getRadius() {
		return radius;
	}

	public void setRadius(Byte radius) {
		this.radius = radius;
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

	public Boolean isCommission() {
		return commission;
	}

	public void setCommission(Boolean commission) {
		this.commission = commission;
	}
}