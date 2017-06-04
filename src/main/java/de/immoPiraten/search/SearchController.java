package de.immoPiraten.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.immoPiraten.query.ResultsSorting;
import de.immoPiraten.realEstate.House;
import de.immoPiraten.realEstate.Portal;
import de.immoPiraten.realEstate.PurchaseType;
import de.immoPiraten.realEstate.RealEstateType;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;	
	
	@RequestMapping("/expose")
	public House getHouse(@RequestParam(value = "portal") int portal,
			@RequestParam(value = "id") int id) {
		
		return this.searchService.getExpose(Portal.values()[portal], id);
	}

	@RequestMapping("/search")
	public List<House> getResponse(@RequestParam(value = "portal", required = false) Integer portal,
			@RequestParam(value = "realestatetype") int realEstateType,
			@RequestParam(value = "purchasetype") int purchaseType, 
			@RequestParam(value = "input") String input,
			@RequestParam(value = "radius") byte radius,
			@RequestParam(value = "freeofcommission", required = false) Boolean freeOfCommission,
			@RequestParam(value = "livingareafrom", required = false) Double livingAreaFrom,
			@RequestParam(value = "livingareatill", required = false) Double livingAreaTill,
			@RequestParam(value = "pricefrom", required = false) Integer priceFrom,
			@RequestParam(value = "pricetill", required = false) Integer priceTill,
			@RequestParam(value = "sorting", required = false) Integer sorting) {
			
		if (portal == null)
			portal = Portal.All.getValue();
		
		if (sorting == null)
			sorting = ResultsSorting.PublicationDateDESC.getValue();
		
		return this.searchService.Search(Portal.values()[portal], RealEstateType.values()[realEstateType], PurchaseType.values()[purchaseType],
				input, radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill, ResultsSorting.values()[sorting]);
	}
}