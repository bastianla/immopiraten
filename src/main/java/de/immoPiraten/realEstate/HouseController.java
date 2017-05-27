package de.immoPiraten.realEstate;

import java.util.List;

import org.json.JSONException;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.immoPiraten.ImmoScout24.GeoAutoCompletionService;
import de.immoPiraten.query.ResultsSorting;

@RestController
public class HouseController {

	@Autowired
	private HouseService houseService;

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/houses")
	public List<House> getHouses(@RequestParam(value = "postcode") String postCode,
			@RequestParam(value = "city") String city, @RequestParam(value = "pricefrom") int priceFrom,
			@RequestParam(value = "pricetill") int priceTill,
			@RequestParam(value = "livingareafrom") short livingAreaFrom,
			@RequestParam(value = "livingareatill") short livingAreaTill,
			@RequestParam(value = "landareafrom") short landAreaFrom,
			@RequestParam(value = "landareatill") short landAreaTill, @RequestParam(value = "roomfrom") short roomFrom,
			@RequestParam(value = "roomtill") short roomTill,
			@RequestParam(value = "constructionyearfrom") short constructionYearFrom,
			@RequestParam(value = "constructionyeartill") short constructionYearTill,
			@RequestParam(value = "balcony") boolean balcony, @RequestParam(value = "terrace") boolean terrace,
			@RequestParam(value = "garden") boolean garden, @RequestParam(value = "garage") boolean garage,
			@RequestParam(value = "commission") boolean commission) {
		return this.houseService.getItems(postCode, city, priceFrom, priceTill, livingAreaFrom, livingAreaTill,
				landAreaFrom, landAreaTill, roomFrom, roomTill, constructionYearFrom, constructionYearTill, balcony,
				terrace, garden, garage, commission);
	}

	@RequestMapping("/expose")
	public House getHouse(@RequestParam(value = "id") int id) {
		return this.houseService.getExpose(id);
	}

	
	@RequestMapping("/search")
	public List<House> getResponse(@RequestParam(value = "realestatetype") int realEstateType,
			@RequestParam(value = "purchasetype") int purchaseType, 
			@RequestParam(value = "input") String input,
			@RequestParam(value = "radius") byte radius,
			@RequestParam(value = "freeofcommission") boolean freeOfCommission,
			@RequestParam(value = "livingareafrom", required = false) Double livingAreaFrom,
			@RequestParam(value = "livingareatill", required = false) Double livingAreaTill,
			@RequestParam(value = "pricefrom", required = false) Integer priceFrom,
			@RequestParam(value = "pricetill", required = false) Integer priceTill,
			@RequestParam(value = "sorting", required = false) Integer sorting) {
		
		if (sorting == null)
			sorting = ResultsSorting.PublicationDateDESC.getValue();
		
		return this.houseService.Search(RealEstateType.values()[realEstateType], PurchaseType.values()[purchaseType],
				GeoAutoCompletionService.ENTITY_TYPE_CITY, input, radius, freeOfCommission, livingAreaFrom,
				livingAreaTill, priceFrom, priceTill, ResultsSorting.values()[sorting]);
	}
}
