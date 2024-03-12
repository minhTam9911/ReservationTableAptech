package com.bookingtable.servicies.implement;

import java.util.List;

import com.bookingtable.mappers.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingtable.dtos.CollectionDto;
import com.bookingtable.dtos.InvoiceDto;
import com.bookingtable.models.Collection;
import com.bookingtable.repositories.CollectionRepository;
import com.bookingtable.servicies.ICollectionService;

@Service
public class CollectionService implements ICollectionService {

	@Autowired
	private CollectionRepository collectionRepository;
	
	@Override
	public Collection getById(Integer id) {
		return collectionRepository.findById(id).get();
	}

	@Override
	public CollectionDto findByCustomerAndRestaurant(String idCustomer, String idRestaurant) {
		var collection =  collectionRepository.findByCustomerIdAndRestaurantId(idCustomer,idRestaurant);
		return CollectionMapper.mapToDto(collection);
	}
	@Override
	public List<Collection> getByCustomer(String idCustomer) {
		return collectionRepository.findByCustomerEmail(idCustomer);
	}

	@Override
	public boolean insert(CollectionDto collectionDto) {
		try {
			var collection = CollectionMapper.mapToModel(collectionDto);
			var check = collectionRepository.findByCustomerEmail(collection.getCustomer().getEmail());
			for (var i : check) {
				if (i.getRestaurant().getId().equals(collection.getRestaurant().getId())) {
					return false;
				}
			}
			if (collectionRepository.save(collection) != null) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean delete(Integer id) {
		var check = collectionRepository.findById(id).get();
			collectionRepository.deleteById(id);
			return true;
	}

}
