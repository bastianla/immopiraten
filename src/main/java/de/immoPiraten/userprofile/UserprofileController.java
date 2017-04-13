package de.immoPiraten.userprofile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserprofileController {

	// autowired says that dependency injection is needs
	@Autowired
	private UserprofileService userprofileService;
	
	// default is get method
	@RequestMapping("/userprofiles")
	public List<Userprofile> getAllUserprofiles(){
		return this.userprofileService.getAllItems();
	}
	
	@RequestMapping("/userprofiles/{id}")
	public Userprofile getUserprofile(@PathVariable int id){
		return this.userprofileService.getItem(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/userprofiles")
	public void addUserprofile(@RequestBody Userprofile userprofile){
		this.userprofileService.addItem(userprofile);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/userprofiles/{id}")
	public void updateUserprofile(@RequestBody Userprofile userprofile, @PathVariable int id){
		this.userprofileService.updateItem(id, userprofile);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/userprofiles/{id}")
	public void deleteUserprofile(@PathVariable int id){
		this.userprofileService.deleteItem(id);
	}	
}