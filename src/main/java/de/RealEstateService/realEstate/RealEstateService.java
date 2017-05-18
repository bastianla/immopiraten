package de.RealEstateService.realEstate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import de.RealEstateService.query.Query;

@Service
public class RealEstateService {

	@PersistenceUnit EntityManagerFactory emf;	
	
	private EntityManager entityManager;
	
	private EntityManager getEntityManager()
	{
		if(this.entityManager == null)
			this.entityManager = emf.createEntityManager();
		
		return this.entityManager;
	}
	
	@Autowired
	private RealEstateRepositoryService repository;
	
	public List<RealEstate> getAllRealEstates() {
		List<RealEstate> realEstates = new ArrayList<>();
		this.repository.findAll().forEach(realEstates::add);
		return realEstates;
	}
	
	public RealEstate getRealEstate(int id){
		return this.repository.findOne(id);
	}
	
	public List<RealEstate> getAllRealEstates(Query query) {

		// Important: The name of the fields must be equal to the names of the properties in the class (CaseSensitive)
		String searchTerm = 							   (((query.getCity() != null) && (!query.getCity().isEmpty())) ? "rs.city = '" + query.getCity() + "'" : "");
		searchTerm += ((query.getConstructionYearFrom() > 0 && query.getConstructionYearTo() > 0) ? (searchTerm.isEmpty() ? "" : " and ") + "rs.constructionYear between " + query.getConstructionYearFrom() + " and " + query.getConstructionYearTo() : "");
		searchTerm += (((query.getPostCode() != null) && (!query.getPostCode().isEmpty())) ? (searchTerm.isEmpty() ? "" : " and ") + "rs.postCode = '" + query.getPostCode() + "'" : ""); 
		
		if (!searchTerm.trim().isEmpty())
			searchTerm = "WHERE " + searchTerm;
		
		// Important: The name of the table must be equal to the class name (CaseSensitive)		
		TypedQuery<RealEstate> q = this.getEntityManager().createQuery("SELECT rs "
					+ "FROM RealEstate rs " + searchTerm,
					// + "WHERE rs.city = '" + query.getCity() + "'",
					RealEstate.class);
		List<RealEstate> realEstates = new ArrayList<>();
		realEstates = q.getResultList();
		return realEstates;
	}	
}
