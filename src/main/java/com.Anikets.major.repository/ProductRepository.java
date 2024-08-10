package com.Anikets.major.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Anikets.major.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findAllByCategory_id(int id);

}
