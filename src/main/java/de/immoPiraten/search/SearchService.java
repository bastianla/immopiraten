package de.immoPiraten.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import de.immoPiraten.query.ResultsSorting;
import de.immoPiraten.realEstate.House;
import de.immoPiraten.realEstate.Portal;
import de.immoPiraten.realEstate.PurchaseType;
import de.immoPiraten.realEstate.RealEstate;
import de.immoPiraten.realEstate.RealEstateType;

@Service
public class SearchService {
	
	public House getExpose(Portal portal, int id) {
		
		House houseExpose = null;
		
		if (portal == Portal.Immonet){
			houseExpose = de.immoPiraten.ownPortal.Search.getExpose(id);	
		}
		else if (portal == Portal.ImmobilienScout24){
			houseExpose = de.immoPiraten.ImmoScout24.Search.getExpose(id);
		}
		
		return houseExpose;
	}
	
	public List<House> Search(Portal portal, RealEstateType realEstateType, PurchaseType purchaseType, String entityType, String input,
			byte radius, Boolean freeOfCommission, Double livingAreaFrom, Double livingAreaTill, Integer priceFrom,
			Integer priceTill, ResultsSorting sorting) {
		
		List<House> results = new ArrayList<House>();

		if (portal == Portal.ImmobilienScout24 || portal == Portal.All){		
			try {
				results.addAll(de.immoPiraten.ImmoScout24.Search.Execute(realEstateType, purchaseType, entityType, input,
						radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill));
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
		
		if (portal == Portal.Immonet || portal == Portal.All){
			try {
				results.addAll(de.immoPiraten.ownPortal.Search.Execute(realEstateType, purchaseType, entityType, input,
						radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill));
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
				
		results.sort(this.getRealEstateComparator(sorting));
		
		return results;
	}	
	
	private Comparator<RealEstate> getRealEstateComparator(ResultsSorting sorting)
	{
		if (sorting == null)
			sorting = ResultsSorting.PublicationDateDESC;
					
		switch (sorting)
		{
			case PublicationDateDESC:
				return Collections.reverseOrder(RealEstate.REAL_ESTATE_PUBLICATION_DATE_COMPARATOR);
				
			case PublicationDateASC:
				return RealEstate.REAL_ESTATE_PUBLICATION_DATE_COMPARATOR;
				
			case PriceDESC:
				return Collections.reverseOrder(RealEstate.REAL_ESTATE_PRICE_COMPARATOR);
				
			case PriceASC:
				return RealEstate.REAL_ESTATE_PRICE_COMPARATOR;
				
			default:
				return Collections.reverseOrder(RealEstate.REAL_ESTATE_PUBLICATION_DATE_COMPARATOR);
		}
	}
}