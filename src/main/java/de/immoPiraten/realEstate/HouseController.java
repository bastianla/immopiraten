package de.immoPiraten.realEstate;

import java.util.List;

import org.json.JSONException;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	
	@RequestMapping("/houses")
	public List<House> getHouses(
			@RequestParam(value="postcode") String postCode,
			@RequestParam(value="city") String city,
			@RequestParam(value="pricefrom") int priceFrom,
			@RequestParam(value="pricetill") int priceTill,
			@RequestParam(value="livingareafrom") short livingAreaFrom,
			@RequestParam(value="livingareatill") short livingAreaTill,
			@RequestParam(value="landareafrom") short landAreaFrom,
			@RequestParam(value="landareatill") short landAreaTill,
			@RequestParam(value="roomfrom") short roomFrom,
			@RequestParam(value="roomtill") short roomTill,
			@RequestParam(value="constructionyearfrom") short constructionYearFrom,
			@RequestParam(value="constructionyeartill") short constructionYearTill,
			@RequestParam(value = "balcony") boolean balcony,
			@RequestParam(value = "terrace") boolean terrace,
			@RequestParam(value = "garden")  boolean garden,
			@RequestParam(value = "garage")  boolean garage,
			@RequestParam(value = "commission")  boolean commission)
	{
		return this.houseService.getItems(
				postCode, city, priceFrom, priceTill, livingAreaFrom, livingAreaTill, landAreaFrom, landAreaTill,
				roomFrom, roomTill, constructionYearFrom, constructionYearTill, balcony, terrace, garden, garage, commission);
	}
	
	@RequestMapping("/expose")
	public String getApi(@RequestParam(value="id") int id)
	{
		return this.houseService.getExpose(id);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/exposeJson")
	public String getApiJson(@RequestParam(value="id") int id)
	{
		String json = this.houseService.getExpose(id);
		try {
			json = XML.toJSONObject(json).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
		}
	
	@RequestMapping("/search")
	public String getResponse()
	{
		List<House> houses =  this.houseService.Search(RealEstateType.House, PurchaseType.Buy, "Aachen");
		
		return "works";
	}
}
