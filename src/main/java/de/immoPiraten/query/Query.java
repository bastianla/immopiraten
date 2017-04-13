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
	private boolean notification;
	private Date lastNotification;
	private PurchaseType purchaseType;
	private double priceFrom;
	private double priceTo;
	private short roomsFrom;
	private short roomsTo;
	private short livingAreaFrom;
	private short livingAreaTo;
	private short landAreaFrom;
	private short landAreaTo;
	private short constructionYearFrom;
	private short constructionYearTo;
	private short radius;
	private boolean balcony;
	private boolean terrace;
	private boolean garden;
	private boolean garage;
	private boolean commission;
	
	public Query(){
	}
	
	public Query(String name, RealEstateType realEstateType, boolean notification, Date lastNotification,
			PurchaseType purchaseType, double priceFrom, double priceTo, short roomsFrom, short roomsTo,
			short livingAreaFrom, short livingAreaTo, short landAreaFrom, short landAreaTo, short constructionYearFrom,
			short constructionYearTo, short radius, boolean balcony, boolean terrace, boolean garden, boolean garage,
			boolean commission) {
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

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
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

	public double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}

	public short getRoomsFrom() {
		return roomsFrom;
	}

	public void setRoomsFrom(short roomsFrom) {
		this.roomsFrom = roomsFrom;
	}

	public short getRoomsTo() {
		return roomsTo;
	}

	public void setRoomsTo(short roomsTo) {
		this.roomsTo = roomsTo;
	}

	public short getLivingAreaFrom() {
		return livingAreaFrom;
	}

	public void setLivingAreaFrom(short livingAreaFrom) {
		this.livingAreaFrom = livingAreaFrom;
	}

	public short getLivingAreaTo() {
		return livingAreaTo;
	}

	public void setLivingAreaTo(short livingAreaTo) {
		this.livingAreaTo = livingAreaTo;
	}

	public short getLandAreaFrom() {
		return landAreaFrom;
	}

	public void setLandAreaFrom(short landAreaFrom) {
		this.landAreaFrom = landAreaFrom;
	}

	public short getLandAreaTo() {
		return landAreaTo;
	}

	public void setLandAreaTo(short landAreaTo) {
		this.landAreaTo = landAreaTo;
	}

	public short getConstructionYearFrom() {
		return constructionYearFrom;
	}

	public void setConstructionYearFrom(short constructionYearFrom) {
		this.constructionYearFrom = constructionYearFrom;
	}

	public short getConstructionYearTo() {
		return constructionYearTo;
	}

	public void setConstructionYearTo(short constructionYearTo) {
		this.constructionYearTo = constructionYearTo;
	}

	public short getRadius() {
		return radius;
	}

	public void setRadius(short radius) {
		this.radius = radius;
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

	public boolean isCommission() {
		return commission;
	}

	public void setCommission(boolean commission) {
		this.commission = commission;
	}
}