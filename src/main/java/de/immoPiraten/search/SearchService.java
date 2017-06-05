package de.immoPiraten.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

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
	
	public List<House> Search(Portal portal, RealEstateType realEstateType, PurchaseType purchaseType, String input,
			byte radius, Boolean freeOfCommission, Short livingAreaFrom, Short livingAreaTill, Integer priceFrom, Integer priceTill, 
			Short constructionYearFrom, Short constructionYearTill, Float roomsFrom, Float roomsTill, Short landAreaFrom, Short landAreaTill,
			Boolean balcony, Boolean terrace, Boolean garden, Boolean garage, ResultsSorting sorting) {
		
		boolean isPostCode = Pattern.matches("\\d{5}", input);
		SearchType searchType = isPostCode ? SearchType.PostCode : SearchType.City;	
		
		List<House> results = new ArrayList<House>();

		if (portal == Portal.ImmobilienScout24 || portal == Portal.All){		
			try {
				results.addAll(de.immoPiraten.ImmoScout24.Search.execute(realEstateType, purchaseType, searchType, input,
						radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill,
						constructionYearFrom, constructionYearTill, roomsFrom, roomsTill, landAreaFrom, landAreaTill,
						balcony, terrace, garden, garage));
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
		
		if (portal == Portal.Immonet || portal == Portal.All){
			try {
				results.addAll(de.immoPiraten.ownPortal.Search.execute(realEstateType, purchaseType, searchType, input,
						radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill,
						constructionYearFrom, constructionYearTill, roomsFrom, roomsTill, landAreaFrom, landAreaTill,
						balcony, terrace, garden, garage));
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