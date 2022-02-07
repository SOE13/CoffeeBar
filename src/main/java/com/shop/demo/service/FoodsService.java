package com.shop.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.demo.modal.Foods;
import com.shop.demo.repo.FoodsReop;

@Service
public class FoodsService {
	@Autowired
	private FoodsReop repo;
	
	public void save(Foods foods) {
		repo.save(foods);
	}
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	public Foods selectOne(Integer id) {
		return repo.findById(id).get();
	}
	public List<Foods> selectAll(){
		return repo.findAll();
	}
	public List<Foods> selectByCat(Integer id){
		return repo.findByCat(id);
	}
}
