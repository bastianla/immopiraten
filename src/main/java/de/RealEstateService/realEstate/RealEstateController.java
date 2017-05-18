package de.RealEstateService.realEstate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.RealEstateService.query.Query;

@RestController
public class RealEstateController {

	@Autowired
	private RealEstateService realEstateService;
	
	@RequestMapping("/realestates/{id}")
	public RealEstate getRealEstate(@PathVariable int id){
		return this.realEstateService.getRealEstate(id);
	}
	
	@RequestMapping("/realestates")
	public List<RealEstate> getAllRealEstates(){
		return this.realEstateService.getAllRealEstates();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/realestates")
	public List<RealEstate> getRealEstates(@RequestBody Query query){
		return this.realEstateService.getAllRealEstates(query);
	}
}