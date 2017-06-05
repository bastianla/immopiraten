package de.RealEstateService.realEstate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.RealEstateService.query.Query;

@RestController
public class RealEstateController {

	@Autowired
	private RealEstateService realEstateService;
	
	@RequestMapping("/realestates/{id}")
	public RealEstate getRealEstate(@PathVariable int id){
		return this.realEstateService.getRealEstate(id);
	}
	
	@RequestMapping("/realestatesAll")
	public List<RealEstate> getAllRealEstates(){
		return this.realEstateService.getAllRealEstates();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/realestates")
	public List<RealEstate> getRealEstates(@RequestBody Query query){
		return this.realEstateService.getAllRealEstates(query);
	}
	
	@RequestMapping("/realestates")
	public List<RealEstate> getRealEstates(
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "postCode", required = false) String postCode,		
			@RequestParam(value = "realEstateType", required = false) Integer realEstateType,
			@RequestParam(value = "purchaseType", required = false) Integer purchaseType,
			@RequestParam(value = "priceFrom", required = false) Integer priceFrom,
			@RequestParam(value = "priceTo", required = false) Integer priceTo,
			@RequestParam(value = "roomsFrom", required = false) Float roomsFrom,
			@RequestParam(value = "roomsTo", required = false) Float roomsTo,
			@RequestParam(value = "livingAreaFrom", required = false) Short livingAreaFrom,
			@RequestParam(value = "livingAreaTo", required = false) Short livingAreaTo,
			@RequestParam(value = "landAreaFrom", required = false) Short landAreaFrom,
			@RequestParam(value = "landAreaTo", required = false) Short landAreaTo,
			@RequestParam(value = "constructionYearFrom", required = false) Short constructionYearFrom,
			@RequestParam(value = "constructionYearTo", required = false) Short constructionYearTo,
			@RequestParam(value = "balcony", required = false) Boolean balcony,
			@RequestParam(value = "terrace", required = false) Boolean terrace,
			@RequestParam(value = "garden", required = false) Boolean garden,
			@RequestParam(value = "garage", required = false) Boolean garage,
			@RequestParam(value = "commission", required = false) Boolean commission) {
		
		Query newQuery = new Query();
		newQuery.setCity(city);
		newQuery.setPostCode(postCode);
		newQuery.setRealEstateType((realEstateType != null) ? RealEstateType.values()[realEstateType] : null);
		newQuery.setPurchaseType((purchaseType != null) ? PurchaseType.values()[purchaseType] : null);
		newQuery.setPriceFrom(priceFrom);
		newQuery.setPriceTo(priceTo);
		newQuery.setRoomsFrom(roomsFrom);
		newQuery.setRoomsTo(roomsTo);
		newQuery.setLivingAreaFrom(livingAreaFrom);
		newQuery.setLivingAreaTo(livingAreaTo);
		newQuery.setLandAreaFrom(landAreaFrom);
		newQuery.setLandAreaTo(landAreaTo);
		newQuery.setConstructionYearFrom(constructionYearFrom);
		newQuery.setConstructionYearTo(constructionYearTo);
		newQuery.setBalcony(balcony);
		newQuery.setTerrace(terrace);
		newQuery.setGarden(garden);
		newQuery.setGarage(garage);
		newQuery.setCommission(commission);
		
		return this.realEstateService.getAllRealEstates(newQuery);
	}	
}