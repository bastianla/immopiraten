package de.immoPiraten.realEstate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlotController {
	
	@Autowired
	private PlotService plotService;
	
	@RequestMapping("/plots")
	public List<Plot> getPlots(
			@RequestParam(value="postcode") String postCode,
			@RequestParam(value="city") String city,
			@RequestParam(value="pricefrom") int priceFrom,
			@RequestParam(value="pricetill") int priceTill,
			@RequestParam(value="landareafrom") short landAreaFrom,
			@RequestParam(value="landareatill") short landAreaTill)
	{
		return this.plotService.getItems(
				postCode, city, priceFrom, priceTill, landAreaFrom, landAreaTill);
	}
}
