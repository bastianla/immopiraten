package de.immoPiraten.ownPortal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.immoPiraten.APIException;
import de.immoPiraten.realEstate.House;
import de.immoPiraten.realEstate.Portal;
import de.immoPiraten.realEstate.PurchaseType;
import de.immoPiraten.realEstate.RealEstateType;
import de.immoPiraten.utility.Parser;

public class Search {
	
	protected final static String URLOwnPortal = "http://localhost:8081/realestates";
	
	@SuppressWarnings("unchecked")
	public static List<House> Execute(RealEstateType realEstateType, PurchaseType purchaseType, String entityType, String input,
			Byte radius, Boolean freeOfCommission, Double livingAreaFrom, Double livingAreaTo, Integer priceFrom,
			Integer priceTo){
		
		String url = Search.buildURL(realEstateType, purchaseType, entityType, input, radius, freeOfCommission, livingAreaFrom, livingAreaTo, priceFrom, priceTo);
		
		String jsonResult;
		try {
			jsonResult = Search.sendRequest(url);
		} catch(Exception e){
			jsonResult = null;
		}
		
		List<House> houseList = new ArrayList<House>();
		
		if (jsonResult != null)
		{
			List<LinkedHashMap<String, Object>> result = null;
			try {
				result = new ObjectMapper().readValue(jsonResult.toString(), List.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (result != null && result.size() > 0){
				
				for (int i = 0; i < result.size(); i++){
					houseList.add(Search.getHouse(result.get(i)));	
				}				
			}
		}
			
		return houseList;
	}
	
	private static String buildURL(RealEstateType realEstateType, PurchaseType purchaseType, String entityType, String input,
			Byte radius, Boolean freeOfCommission, Double livingAreaFrom, Double livingAreaTo, Integer priceFrom,
			Integer priceTo)
	{
		URIBuilder uriBuilder = null;
		try {
			uriBuilder = new URIBuilder(Search.URLOwnPortal);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (realEstateType != null)
			uriBuilder.addParameter("realEstateType", Integer.toString(realEstateType.ordinal()));
		
		if (purchaseType != null)
			uriBuilder.addParameter("purchaseType", Integer.toString(purchaseType.ordinal()));		
		
		if (entityType != null)
		{
			if (entityType == "city")
				uriBuilder.addParameter("city", input);
			else if (entityType == "postCode")
				uriBuilder.addParameter("postCode", input);
		}

		if (freeOfCommission != null)
			uriBuilder.addParameter("commission", freeOfCommission.toString());
		
		if (livingAreaFrom != null)
			uriBuilder.addParameter("landAreaFrom", livingAreaFrom.toString());

		if (livingAreaTo != null)
			uriBuilder.addParameter("livingAreaTo", livingAreaTo.toString());
		
		if (priceFrom != null)
			uriBuilder.addParameter("priceFrom", priceFrom.toString());		
		
		if (priceTo != null)
			uriBuilder.addParameter("priceTo", priceTo.toString());		
		
		return uriBuilder.toString();
	}

	private static String sendRequest(String url){
		
		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");
		
		HttpClient client = HttpClientBuilder.create().build();
		String response;
		try {
			response = client.execute(request, new BasicResponseHandler());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new APIException("An error occured during sending the request.", e);
		}
		
		return response;
	}
	
	private static House getHouse(LinkedHashMap<String, Object> jsonHouseElement){
		House newHouse = new House();
		
		newHouse.setId(Integer.parseInt(jsonHouseElement.get("id").toString()));
		newHouse.setPortal(Portal.Immowelt);
		
		String title = Parser.parseString(jsonHouseElement.get("title"));
		if (title != null)
			newHouse.setTitle(title);		
		
		String description = Parser.parseString(jsonHouseElement.get("description"));
		if (description != null)
			newHouse.setDescription(description);
		
		Float additionalCosts = Parser.parseFloat(jsonHouseElement.get("additionalCosts"));
		if (additionalCosts != null)
			newHouse.setAdditionalCosts(additionalCosts);
		
		Integer construction = Parser.parseInteger(jsonHouseElement.get("constructionYear"));
		if (construction != null)
			newHouse.setConstruction(construction);
		
		Boolean energyCertificate = Parser.parseBoolean(jsonHouseElement.get("energyCertificate"));
		if (energyCertificate != null)
			newHouse.setEnergyCertificate(energyCertificate);
		
		Double energyConsumption = Parser.parseDouble(jsonHouseElement.get("energyConsumption"));
		if (energyConsumption != null)
			newHouse.setEnergyConsumption(energyConsumption);
		
		Boolean garage = Parser.parseBoolean(jsonHouseElement.get("garage"));
		if (garage != null)
			newHouse.setGarage(garage);		

		Boolean garden = Parser.parseBoolean(jsonHouseElement.get("garden"));
		if (garden != null)
			newHouse.setGarden(garden);		

		Double landArea = Parser.parseDouble(jsonHouseElement.get("landArea"));
		if (landArea != null)
			newHouse.setEnergyConsumption(landArea);	
		
		String link = Parser.parseString(jsonHouseElement.get("link"));
		if (link != null)
			newHouse.setLink(link);		
		
		Double livingArea = Parser.parseDouble(jsonHouseElement.get("livingArea"));
		if (livingArea != null)
			newHouse.setLivingArea(livingArea);
		
		Double price = Parser.parseDouble(jsonHouseElement.get("price"));
		if (price != null)
			newHouse.setPrice(price);		
		
		Double room = Parser.parseDouble(jsonHouseElement.get("room"));
		if (room != null)
			newHouse.setRoom(room);			
		
		Boolean terrace = Parser.parseBoolean(jsonHouseElement.get("terrace"));
		if (terrace != null)
			newHouse.setTerrace(terrace);
		
		/*newHouse.setAdditionalCosts(Float.parseFloat(jsonHouseElement.get("additionalCosts").toString()));
		newHouse.setConstruction(Integer.parseInt(jsonHouseElement.get("construction").toString()));
		newHouse.setEnergyCertificate(Boolean.parseBoolean(jsonHouseElement.get("energyCertificate").toString()));
		newHouse.setEnergyConsumption(Double.parseDouble(jsonHouseElement.get("energyConsumption").toString()));
		newHouse.setGarage(Boolean.parseBoolean(jsonHouseElement.get("garage").toString()));
		newHouse.setGarden(Boolean.parseBoolean(jsonHouseElement.get("garden").toString()));
		newHouse.setLandArea(Double.parseDouble(jsonHouseElement.get("landArea").toString()));
		newHouse.setLink(jsonHouseElement.get("link").toString());
		newHouse.setLivingArea(Double.parseDouble(jsonHouseElement.get("livingArea").toString()));
		newHouse.setPrice(Double.parseDouble(jsonHouseElement.get("price").toString()));
		newHouse.setRoom(Double.parseDouble(jsonHouseElement.get("room").toString()));
		newHouse.setTerrace(Boolean.parseBoolean(jsonHouseElement.get("terrace").toString()));*/
		
		// newHouse.setAvailabilityDate(availabilityDate);
		// newHouse.setHeater(heater);
		// newHouse.setPublicationDate(publicationDate);
		// newHouse.setPurchaseType(purchaseType);
		
		/*Site newSite = new Site(jsonHouseElement.get("country").toString());
		newSite.setCity(jsonHouseElement.get("city").toString());
		newSite.setHouseNumber(jsonHouseElement.get("houseNumber").toString());
		newSite.setPostCode(jsonHouseElement.get("postCode").toString());
		newSite.setStreet(jsonHouseElement.get("street").toString());		
		newHouse.setSite(newSite);*/
		
		return newHouse;
	}
}