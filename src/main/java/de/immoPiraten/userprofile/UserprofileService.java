package de.immoPiraten.userprofile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import de.immoPiraten.crudRepository.CrudRepositoryService;
import de.immoPiraten.favorite.Favorite;

@Service
public class UserprofileService extends CrudRepositoryService<CrudRepository<Userprofile, Integer>, Userprofile> {

	@Autowired
	private UserprofileRepository userprofileRepository;
	
	public List<Userprofile> getAllUserprofiles() {
		List<Userprofile> userprofiles = new ArrayList<>();
		this.userprofileRepository.findAll().forEach(userprofiles::add);
		return userprofiles;
	}
	
	public Userprofile getUserprofile(int id){
		return this.userprofileRepository.findOne(id);
	}

	public void addUserprofile(Userprofile userprofile) {
		this.userprofileRepository.save(userprofile);
	}
	
	public void updateUserprofile(int id, Userprofile userprofile) {
		this.userprofileRepository.save(userprofile);
	}

	public void deleteUserprofile(int id) {
		this.userprofileRepository.delete(id);
	}	
	
	public void AddFavorite(int id, String link)
	{
		Userprofile profile = this.userprofileRepository.findOne(id);
		
		Favorite newFavorite = new Favorite();
		newFavorite.setUserprofile(profile);
		newFavorite.setRealEstate(null);
		
		newFavorite.setCreationDate(new Date());
		
		List<Favorite> favorites = profile.getFavorites();
		favorites.add(newFavorite);
	
		this.updateUserprofile(id, profile);
	}
	
	public List<Favorite> getFavorites(int id) {
		Userprofile profile = this.userprofileRepository.findOne(id);
		
		return profile.getFavorites();
	}
}