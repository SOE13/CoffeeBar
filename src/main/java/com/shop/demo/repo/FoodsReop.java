package com.shop.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.demo.modal.Foods;
@Repository
public interface FoodsReop extends JpaRepository<Foods, Integer>{
	
	@Query("Select f From Foods f Where f.cat=?1")
	List<Foods> findByCat(Integer id);
}
