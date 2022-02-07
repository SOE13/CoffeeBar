package com.shop.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.demo.modal.SellFoods;
@Repository
public interface SellFoodRepo extends JpaRepository<SellFoods, Integer>{
	@Query("Select s From SellFoods s Order By s.invoice Desc")
	List<SellFoods> findByInvoice();
	
	@Query("Select s From SellFoods s Where s.invoice=?1")
	List<SellFoods> findByInvoice(Integer id);
}
