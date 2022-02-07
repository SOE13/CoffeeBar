package com.shop.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.demo.modal.Foods;
import com.shop.demo.modal.SellFoods;
import com.shop.demo.service.FoodsService;
import com.shop.demo.service.SellFoodsService;

@RestController
public class API {
	@Autowired
	private FoodsService service;
	
	@Autowired
	private SellFoodsService sellfoodservice;
	
	@GetMapping("/getCatItem")
	public Foods getCatItem(@Param("id") Integer id) {
		return service.selectOne(id);
	}
	@GetMapping("/confirm")
	public String confirm(@Param("id") Integer id,@Param("count")Integer count) {
		
		Foods food=service.selectOne(id);
		SellFoods sellfood=new SellFoods();
		sellfood.setCount(count);
		sellfood.setFoods(food);
		sellfood.setDate(new Date());

		SellFoods sellfoodInv=sellfoodservice.selectByInvoice();
		if(sellfoodInv==null) {
			sellfood.setInvoice(1);
		}else {
			sellfood.setInvoice(sellfoodInv.getInvoice()+1);
		}
		
		sellfoodservice.save(sellfood);
		
		return "Thank For Your Buying!";
	}
}
