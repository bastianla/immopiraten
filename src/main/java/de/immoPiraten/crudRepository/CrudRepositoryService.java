package de.immoPiraten.crudRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class CrudRepositoryService<TRepositoryType extends CrudRepository<TItemType, Integer>, TItemType> {
	
	@Autowired
	private TRepositoryType repository;
	
	public List<TItemType> getAllItems() {
		List<TItemType> items = new ArrayList<>();
		this.repository.findAll().forEach(items::add);
		return items;
	}
	
	public TItemType getItem(int id){
		return this.repository.findOne(id);
	}

	public void addItem(TItemType item) {
		this.repository.save(item);
	}
	
	public void updateItem(int id, TItemType item) {
		this.repository.save(item);
	}

	public void deleteItem(int id) {
		this.repository.delete(id);
	}	
}