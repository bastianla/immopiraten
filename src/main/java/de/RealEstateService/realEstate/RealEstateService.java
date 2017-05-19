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
		String searchTerm = this.prepareStringValue("city", query.getCity());
		searchTerm = this.prepareAndTerm(searchTerm, this.prepareGreaterValue("constructionYear", query.getConstructionYearFrom())); 
		searchTerm = this.prepareAndTerm(searchTerm, this.prepareLessValue("constructionYear", query.getConstructionYearTo()));			
		searchTerm = this.prepareAndTerm(searchTerm, this.prepareStringValue("postCode", query.getPostCode()));
			
		if (!searchTerm.trim().isEmpty())
			searchTerm = "WHERE " + searchTerm;
		
		// Important: The name of the table must be equal to the class name (CaseSensitive)		
		TypedQuery<RealEstate> q = this.getEntityManager().createQuery("SELECT rs "
					+ "FROM RealEstate rs " + searchTerm,
					RealEstate.class);
		List<RealEstate> realEstates = new ArrayList<>();
		realEstates = q.getResultList();
		return realEstates;
	}
	
	private String prepareAndTerm(String searchTerm, String newSearchTerm){
		return ((!this.isNullOrEmpty(searchTerm)) && (!this.isNullOrEmpty(newSearchTerm))) ? searchTerm + " AND " + newSearchTerm : ((!this.isNullOrEmpty(searchTerm)) ? searchTerm : newSearchTerm);
	}
	
	private boolean isNullOrEmpty(String text){
		return (text == null) || ((text != null) && (text.trim().isEmpty()));
	}
	
	private String prepareStringValue(String nameOfParamter, String valueOfParameter){		
		return ((!this.isNullOrEmpty(valueOfParameter)) ? "rs." + nameOfParamter + " = '" + valueOfParameter + "'" : "");
	}
		
	private <T> String prepareGreaterValue(String nameOfParamter, T valueOfParameter){
		return this.prepareCompare(nameOfParamter, valueOfParameter, ">=");
	}
	
	private <T> String prepareLessValue(String nameOfParamter, T valueOfParameter){
		return this.prepareCompare(nameOfParamter, valueOfParameter, "<=");
	}
	
	private <T> String prepareCompare(String nameOfParamter, T valueOfParameter, String operator){
		return ((valueOfParameter != null) ? "rs." + nameOfParamter + " " + operator + " " + valueOfParameter.toString() : "");
	} 
}
