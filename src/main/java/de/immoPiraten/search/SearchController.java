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
			@RequestParam(value = "livingareafrom", required = false) Short livingAreaFrom,
			@RequestParam(value = "livingareatill", required = false) Short livingAreaTill,
			@RequestParam(value = "pricefrom", required = false) Integer priceFrom,
			@RequestParam(value = "pricetill", required = false) Integer priceTill,
			@RequestParam(value = "constructionyearfrom", required = false) Short constructionYearFrom,
			@RequestParam(value = "constructionyeartill", required = false) Short constructionYearTill,
			@RequestParam(value = "roomfrom", required = false) Float roomsFrom,
			@RequestParam(value = "roomtill", required = false) Float roomsTill,
			@RequestParam(value = "landAreafrom", required = false) Short landAreaFrom,
			@RequestParam(value = "landAreafill", required = false) Short landAreaTill,
			@RequestParam(value = "balcony", required = false) Boolean balcony,			
			@RequestParam(value = "terrace", required = false) Boolean terrace,
			@RequestParam(value = "garden", required = false) Boolean garden,
			@RequestParam(value = "garage", required = false) Boolean garage,			
			@RequestParam(value = "sorting", required = false) Integer sorting) {
			
		if (portal == null)
			portal = Portal.All.getValue();
		
		if (sorting == null)
			sorting = ResultsSorting.PublicationDateDESC.getValue();
		
		return this.searchService.Search(Portal.values()[portal], RealEstateType.values()[realEstateType], PurchaseType.values()[purchaseType],
				input, radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill, 
				constructionYearFrom, constructionYearTill, roomsFrom, roomsTill, landAreaFrom, landAreaTill, balcony, terrace, garden, garage,
				ResultsSorting.values()[sorting]);
	}
}