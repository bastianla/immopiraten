package de.RealEstateService.query;

import de.RealEstateService.realEstate.PurchaseType;
import de.RealEstateService.realEstate.RealEstateType;

public class Query {

	private RealEstateType realEstateType;
	private PurchaseType purchaseType;
	private Double priceFrom;
	private Double priceTo;
	private Short roomsFrom;
	private Short roomsTo;
	private Short livingAreaFrom;
	private Short livingAreaTo;
	private Short landAreaFrom;
	private Short landAreaTo;
	private Short constructionYearFrom;
	private Short constructionYearTo;
	private Short radius;
	private Boolean balcony;
	private Boolean terrace;
	private Boolean garden;
	private Boolean garage;
	private Boolean commission;
	private String city;
	private String postCode;
	
	public RealEstateType getRealEstateType() {
		return realEstateType;
	}

	public void setRealEstateType(RealEstateType realEstateType) {
		this.realEstateType = realEstateType;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}

	public Short getRoomsFrom() {
		return roomsFrom;
	}

	public void setRoomsFrom(short roomsFrom) {
		this.roomsFrom = roomsFrom;
	}

	public Short getRoomsTo() {
		return roomsTo;
	}

	public void setRoomsTo(short roomsTo) {
		this.roomsTo = roomsTo;
	}

	public Short getLivingAreaFrom() {
		return livingAreaFrom;
	}

	public void setLivingAreaFrom(short livingAreaFrom) {
		this.livingAreaFrom = livingAreaFrom;
	}

	public Short getLivingAreaTo() {
		return livingAreaTo;
	}

	public void setLivingAreaTo(short livingAreaTo) {
		this.livingAreaTo = livingAreaTo;
	}

	public Short getLandAreaFrom() {
		return landAreaFrom;
	}

	public void setLandAreaFrom(short landAreaFrom) {
		this.landAreaFrom = landAreaFrom;
	}

	public Short getLandAreaTo() {
		return landAreaTo;
	}

	public void setLandAreaTo(short landAreaTo) {
		this.landAreaTo = landAreaTo;
	}

	public Short getConstructionYearFrom() {
		return constructionYearFrom;
	}

	public void setConstructionYearFrom(short constructionYearFrom) {
		this.constructionYearFrom = constructionYearFrom;
	}

	public Short getConstructionYearTo() {
		return constructionYearTo;
	}

	public void setConstructionYearTo(short constructionYearTo) {
		this.constructionYearTo = constructionYearTo;
	}

	public Short getRadius() {
		return radius;
	}

	public void setRadius(short radius) {
		this.radius = radius;
	}

	public Boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public Boolean isTerrace() {
		return terrace;
	}

	public void setTerrace(boolean terrace) {
		this.terrace = terrace;
	}

	public Boolean isGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	public Boolean isGarage() {
		return garage;
	}

	public void setGarage(boolean garage) {
		this.garage = garage;
	}

	public Boolean isCommission() {
		return commission;
	}

	public void setCommission(boolean commission) {
		this.commission = commission;
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
}