package com.Anikets.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Anikets.major.model.Product;
import com.Anikets.major.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository prodRepository;
	public List<Product> getAllProduct(){
		return prodRepository.findAll();
	}
	public void addProduct(Product product) {
		prodRepository.save(product);
	}
	public void removeProductById(long id) {
		prodRepository.deleteById(id);
	}
	public Optional<Product> getProductById(long id){
		return prodRepository.findById(id);
	}
	public List<Product> getAllProductByCategoryId(int id){
		  return prodRepository.findAllByCategory_id(id);
	  }

}
