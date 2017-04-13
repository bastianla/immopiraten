package de.immoPiraten.userprofile;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import de.immoPiraten.crudRepository.CrudRepositoryService;

@Service
public class UserprofileService extends CrudRepositoryService<CrudRepository<Userprofile, Integer>, Userprofile> {

	/*@Autowired
	private UserprofileRepository userprofileRepository;
	
	public List<Userprofile> getAllUserprofiles() {
		List<Userprofile> userprofiles = new ArrayList<>();
		this.userprofileRepository.findAll().forEach(userprofiles::add);
		return userprofiles;
	}
	
	public Userprofile getUserprofile(int id){
		return this.userprofileRepository.findOne(id);
	}

	public void addUserprofiel(Userprofile userprofile) {
		this.userprofileRepository.save(userprofile);
	}
	
	public void updateUserprofile(int id, Userprofile userprofile) {
		this.userprofileRepository.save(userprofile);
	}

	public void deleteTopic(int id) {
		this.userprofileRepository.delete(id);
	}*/	
}