package com.shop.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.demo.modal.SellFoods;
import com.shop.demo.repo.SellFoodRepo;

@Repository
public class SellFoodsService {
	@Autowired
	private SellFoodRepo repo;
	
	public void save(SellFoods sellFoods) {
			repo.save(sellFoods);
	}
	public List<SellFoods> selectAll(){
		return repo.findAll();
	}
	public SellFoods selectByInvoice() {
		if(repo.findByInvoice().size()==0) {
			return null;
		}else {
			return repo.findByInvoice().get(0);
		}
		
	}
	public List<SellFoods> selectByInvoice(Integer id){
		return repo.findByInvoice(id);
	}
}
