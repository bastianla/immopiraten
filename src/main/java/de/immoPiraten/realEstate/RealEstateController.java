package de.immoPiraten.realEstate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealEstateController {

	@Autowired
	private RealEstateService realEstateService;

	@RequestMapping("/realestates")
	public List<RealEstate> getRealEstates(
			@RequestParam(value="postcode") String postCode,
			@RequestParam(value="city") String city,
			@RequestParam(value="pricefrom") int priceFrom,
			@RequestParam(value="pricetill") int priceTill)
	{
		return this.realEstateService.getItems(postCode, city, priceFrom, priceTill);
	}
}
