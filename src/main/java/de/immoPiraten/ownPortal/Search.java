package de.immoPiraten.ownPortal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.immoPiraten.APIException;
import de.immoPiraten.contact.Contact;
import de.immoPiraten.realEstate.BuildingEnergyRatingType;
import de.immoPiraten.realEstate.ConditionType;
import de.immoPiraten.realEstate.FiringType;
import de.immoPiraten.realEstate.HeatingType;
import de.immoPiraten.realEstate.House;
import de.immoPiraten.realEstate.InteriorQualityType;
import de.immoPiraten.realEstate.Portal;
import de.immoPiraten.realEstate.PurchaseType;
import de.immoPiraten.realEstate.RealEstateType;
import de.immoPiraten.realEstate.ResidentialRealEstateType;
import de.immoPiraten.search.SearchType;
import de.immoPiraten.site.Site;
import de.immoPiraten.utility.Parser;

public class Search {
	
	protected final static String URLOwnPortal = "http://localhost:8081/realestates";
	
	@SuppressWarnings("unchecked")
	public static House getExpose(int id){
		String url = Search.URLOwnPortal + "/" + Integer.toString(id);
		
		String jsonResult;
		try {
			jsonResult = Search.sendRequest(url);
		} catch(Exception e){
			jsonResult = null;
		}		 
		
		House newHouse = null;
		
		if (jsonResult != null)
		{		
			LinkedHashMap<String, Object> result = null;
			try {
				result = new ObjectMapper().readValue(jsonResult.toString(), LinkedHashMap.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (result != null && result.size() > 0){
				newHouse = Search.getHouse(result);				
			}
		}
		
		return newHouse;
	}
	
	@SuppressWarnings("unchecked")
	public static List<House> execute(RealEstateType realEstateType, PurchaseType purchaseType, SearchType searchType, String input,
			Byte radius, Boolean freeOfCommission, Short livingAreaFrom, Short livingAreaTo, Integer priceFrom, Integer priceTo,
			Short constructionYearFrom, Short constructionYearTo, Float roomsFrom, Float roomsTo, Short landAreaFrom, Short landAreaTo,
			Boolean balcony, Boolean terrace, Boolean garden, Boolean garage){
		
		String url = Search.buildURL(realEstateType, purchaseType, searchType, input, radius, freeOfCommission, livingAreaFrom, livingAreaTo, priceFrom, priceTo,
									 constructionYearFrom, constructionYearTo, roomsFrom, roomsTo, landAreaFrom, landAreaTo, balcony, terrace, garden, garage);
		
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
	
	private static String buildURL(RealEstateType realEstateType, PurchaseType purchaseType, SearchType searchType, String input,
			Byte radius, Boolean freeOfCommission, Short livingAreaFrom, Short livingAreaTo, Integer priceFrom, Integer priceTo,
			Short constructionYearFrom, Short constructionYearTo, Float roomsFrom, Float roomsTo, Short landAreaFrom, Short landAreaTo,
			Boolean balcony, Boolean terrace, Boolean garden, Boolean garage)
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
		
		if (searchType != null)
		{
			if (searchType == SearchType.City)
				uriBuilder.addParameter("city", input);
			else if (searchType == SearchType.PostCode)
				uriBuilder.addParameter("postCode", input);
		}

		if (freeOfCommission != null)
			uriBuilder.addParameter("commission", freeOfCommission.toString());
		
		if (livingAreaFrom != null)
			uriBuilder.addParameter("livingAreaFrom", livingAreaFrom.toString());

		if (livingAreaTo != null)
			uriBuilder.addParameter("livingAreaTo", livingAreaTo.toString());
		
		if (priceFrom != null)
			uriBuilder.addParameter("priceFrom", priceFrom.toString());		
		
		if (priceTo != null)
			uriBuilder.addParameter("priceTo", priceTo.toString());
		
		if (constructionYearFrom != null)
			uriBuilder.addParameter("constructionYearFrom", constructionYearFrom.toString());

		if (constructionYearTo != null)
			uriBuilder.addParameter("constructionYearTo", constructionYearTo.toString());
		
		if (roomsFrom != null)
			uriBuilder.addParameter("roomsFrom", roomsFrom.toString());

		if (roomsTo != null)
			uriBuilder.addParameter("roomsTo", roomsTo.toString());		

		if (landAreaFrom != null)
			uriBuilder.addParameter("landAreaFrom", landAreaFrom.toString());

		if (landAreaTo != null)
			uriBuilder.addParameter("landAreaTo", landAreaTo.toString());			
		
		if (balcony != null)
			uriBuilder.addParameter("balcony", balcony.toString());		

		if (terrace != null)
			uriBuilder.addParameter("terrace", terrace.toString());		
		
		if (garden != null)
			uriBuilder.addParameter("garden", garden.toString());		
		
		if (garage != null)
			uriBuilder.addParameter("garage", garage.toString());			
		
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
		
		Integer id = Parser.parseInteger(jsonHouseElement.get("id"));
		if (id != null)
			newHouse.setId(id);				
		
		newHouse.setPortal(Portal.Immonet);
		
		String title = Parser.parseString(jsonHouseElement.get("title"));
		if (title != null)
			newHouse.setTitle(title);		
		
		String description = Parser.parseString(jsonHouseElement.get("description"));
		if (description != null)
			newHouse.setDescription(description);
		
		Float additionalCosts = Parser.parseFloat(jsonHouseElement.get("additionalCosts"));
		if (additionalCosts != null)
			newHouse.setAdditionalCosts(additionalCosts);
		
		Short construction = Parser.parseShort(jsonHouseElement.get("constructionYear"));
		if (construction != null)
			newHouse.setConstruction(construction);
		
		Boolean energyCertificate = Parser.parseBoolean(jsonHouseElement.get("energyCertificate"));
		if (energyCertificate != null)
			newHouse.setEnergyCertificate(energyCertificate);
		
		Float energyConsumption = Parser.parseFloat(jsonHouseElement.get("energyConsumption"));
		if (energyConsumption != null)
			newHouse.setEnergyConsumption(energyConsumption);
		
		Boolean garage = Parser.parseBoolean(jsonHouseElement.get("garage"));
		if (garage != null)
			newHouse.setGarage(garage);		

		Boolean garden = Parser.parseBoolean(jsonHouseElement.get("garden"));
		if (garden != null)
			newHouse.setGarden(garden);		

		Short landArea = Parser.parseShort(jsonHouseElement.get("landArea"));
		if (landArea != null)
			newHouse.setEnergyConsumption(landArea);	
		
		String link = Parser.parseString(jsonHouseElement.get("link"));
		if (link != null)
			newHouse.setLink(link);		
		
		Short livingArea = Parser.parseShort(jsonHouseElement.get("livingArea"));
		if (livingArea != null)
			newHouse.setLivingArea(livingArea);
		
		Integer price = Parser.parseInteger(jsonHouseElement.get("price"));
		if (price != null)
			newHouse.setPrice(price);		
		
		Float room = Parser.parseFloat(jsonHouseElement.get("room"));
		if (room != null)
			newHouse.setRoom(room);
		
		Boolean terrace = Parser.parseBoolean(jsonHouseElement.get("terrace"));
		if (terrace != null)
			newHouse.setTerrace(terrace);

		Boolean balcony = Parser.parseBoolean(jsonHouseElement.get("balcony"));
		if (terrace != null)
			newHouse.setBalcony(balcony);		
		
		Date publicationDate = Parser.parseDate(jsonHouseElement.get("publicationDate"));		
		if (publicationDate != null)
			newHouse.setPublicationDate(publicationDate);
		
		Date availabilityDate = Parser.parseDate(jsonHouseElement.get("availabilityDate"));		
		if (availabilityDate != null)
			newHouse.setAvailabilityDate(availabilityDate);		
				
		HeatingType heating = Parser.parseEnum(jsonHouseElement.get("heating"), HeatingType.class);
		if (heating != null)
			newHouse.setHeating(heating);
		
		PurchaseType purchaseType = Parser.parseEnum(jsonHouseElement.get("purchaseType"), PurchaseType.class);
		if (purchaseType != null)
			newHouse.setPurchaseType(purchaseType);
		
		String image = Parser.parseString(jsonHouseElement.get("image"));		
		if (image != null)
			newHouse.setImage(image);
		
		Short floor = Parser.parseShort(jsonHouseElement.get("floor"));
		if (floor != null)
			newHouse.setFloor(floor);

		Short numberOfFloors = Parser.parseShort(jsonHouseElement.get("numberOfFloors"));
		if (numberOfFloors != null)
			newHouse.setNumberOfFloors(numberOfFloors);
		
		Short numberOfBedRooms = Parser.parseShort(jsonHouseElement.get("numberOfBedRooms"));
		if (numberOfBedRooms != null)
			newHouse.setNumberOfBedRooms(numberOfBedRooms);		
		
		Short numberOfBathRooms = Parser.parseShort(jsonHouseElement.get("numberOfBathRooms"));
		if (numberOfBathRooms != null)
			newHouse.setNumberOfBathRooms(numberOfBathRooms);		
		
		Short numberOfParkingSpaces = Parser.parseShort(jsonHouseElement.get("numberOfParkingSpaces"));
		if (numberOfParkingSpaces != null)
			newHouse.setNumberOfParkingSpaces(numberOfParkingSpaces);		
		
		Float heatingCosts = Parser.parseFloat(jsonHouseElement.get("heatingCosts"));
		if (numberOfParkingSpaces != null)
			newHouse.setHeatingCosts(heatingCosts);			
		
		Boolean heatingCostsInAdditionalCosts = Parser.parseBoolean(jsonHouseElement.get("heatingCostsInAdditionalCosts"));
		if (heatingCostsInAdditionalCosts != null)
			newHouse.setHeatingCostsInAdditionalCosts(heatingCostsInAdditionalCosts);				
		
		String deposit = Parser.parseString(jsonHouseElement.get("deposit"));
		if (deposit != null)
			newHouse.setDeposit(deposit);
		
		Float thermalCharacteristic = Parser.parseFloat(jsonHouseElement.get("thermalCharacteristic"));
		if (thermalCharacteristic != null)
			newHouse.setThermalCharacteristic(thermalCharacteristic);

		String locationNote = Parser.parseString(jsonHouseElement.get("locationNote"));
		if (locationNote != null)
			newHouse.setLocationNote(locationNote);		

		String otherNote = Parser.parseString(jsonHouseElement.get("otherNote"));
		if (otherNote != null)
			newHouse.setOtherNote(otherNote);		
		
		String furnishingNote = Parser.parseString(jsonHouseElement.get("furnishingNote"));
		if (furnishingNote != null)
			newHouse.setFurnishingNote(furnishingNote);
		
		String energyEfficiencyClass = Parser.parseString(jsonHouseElement.get("energyEfficiencyClass"));
		if (energyEfficiencyClass != null)
			newHouse.setEnergyEfficiencyClass(energyEfficiencyClass);
		
		ConditionType objectstate = Parser.parseEnum(jsonHouseElement.get("objectstate"), ConditionType.class);
		if (objectstate != null)
			newHouse.setObjectstate(objectstate);
		
		InteriorQualityType interiorQuality = Parser.parseEnum(jsonHouseElement.get("interiorQuality"), InteriorQualityType.class);
		if (interiorQuality != null)
			newHouse.setInteriorQuality(interiorQuality);
		
		BuildingEnergyRatingType buildingEnergyRating = Parser.parseEnum(jsonHouseElement.get("buildingEnergyRating"), BuildingEnergyRatingType.class);
		if (buildingEnergyRating != null)
			newHouse.setBuildingEnergyRating(buildingEnergyRating);
		
		FiringType firing = Parser.parseEnum(jsonHouseElement.get("firing"), FiringType.class);
		if (firing != null)
			newHouse.setFiring(firing);
		
		ResidentialRealEstateType residentialRealEstate = Parser.parseEnum(jsonHouseElement.get("residentialRealEstate"), ResidentialRealEstateType.class);
		if (residentialRealEstate != null)
			newHouse.setResidentialRealEstate(residentialRealEstate);
		
		Contact newContact = new Contact();
		
		de.immoPiraten.contact.TitleType contactTitle = Parser.parseEnum(jsonHouseElement.get("contactTitle"), de.immoPiraten.contact.TitleType.class);
		if (contactTitle != null)
			newContact.setTitle(contactTitle);
		
		String contactFirstName = Parser.parseString(jsonHouseElement.get("contactFirstName"));
		if (contactFirstName != null)
			newContact.setFirstName(contactFirstName);		
		
		String contactLastName = Parser.parseString(jsonHouseElement.get("contactLastName"));
		if (contactLastName != null)
			newContact.setLastName(contactLastName);
		
		String contactTelephone = Parser.parseString(jsonHouseElement.get("contactTelephone"));
		if (contactTelephone != null)
			newContact.setTelephone(contactTelephone);
		
		String contactEmail = Parser.parseString(jsonHouseElement.get("contactEmail"));
		if (contactEmail != null)
			newContact.setEmail(contactEmail);
		
		String contactMobile = Parser.parseString(jsonHouseElement.get("contactMobile"));
		if (contactMobile != null)
			newContact.setMobile(contactMobile);
		
		String contactCompany = Parser.parseString(jsonHouseElement.get("contactCompany"));
		if (contactCompany != null)
			newContact.setCompany(contactCompany);
		
		newHouse.setContact(newContact);
		
		Site newContactSite = new Site(null);
		
		String siteStreet = Parser.parseString(jsonHouseElement.get("siteStreet"));
		if (siteStreet != null)
			newContactSite.setStreet(siteStreet);		

		String siteCity = Parser.parseString(jsonHouseElement.get("siteCity"));
		if (siteCity != null)
			newContactSite.setCity(siteCity);		

		String sitePostCode = Parser.parseString(jsonHouseElement.get("sitePostCode"));
		if (sitePostCode != null)
			newContactSite.setPostCode(sitePostCode);		
		
		String siteHouseNumber = Parser.parseString(jsonHouseElement.get("siteHouseNumber"));
		if (siteHouseNumber != null)
			newContactSite.setHouseNumber(siteHouseNumber);		

		String siteCountry = Parser.parseString(jsonHouseElement.get("siteCountry"));
		if (siteCountry != null)
			newContactSite.setCountry(siteCountry);		
				
		newContact.setSite(newContactSite);		
		
		Site newSite = new Site(null);
		
		String country = Parser.parseString(jsonHouseElement.get("country"));
		if (country != null)
			newSite.setCountry(country);		
		
		String city = Parser.parseString(jsonHouseElement.get("city"));
		if (city != null)
			newSite.setCity(city);		
		
		String houseNumber = Parser.parseString(jsonHouseElement.get("houseNumber"));
		if (houseNumber != null)
			newSite.setHouseNumber(houseNumber);			

		String postCode = Parser.parseString(jsonHouseElement.get("postCode"));
		if (postCode != null)
			newSite.setPostCode(postCode);			
		
		String street = Parser.parseString(jsonHouseElement.get("street"));
		if (street != null)
			newSite.setStreet(street);		
		
		newHouse.setSite(newSite);
		
		return newHouse;
	}
}