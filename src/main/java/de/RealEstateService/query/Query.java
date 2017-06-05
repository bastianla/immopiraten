package de.RealEstateService.query;

import de.RealEstateService.realEstate.PurchaseType;
import de.RealEstateService.realEstate.RealEstateType;

public class Query {

	private RealEstateType realEstateType;
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