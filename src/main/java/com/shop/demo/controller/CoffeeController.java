package com.shop.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.demo.modal.Foods;
import com.shop.demo.modal.Invoice;
import com.shop.demo.modal.SellFoods;
import com.shop.demo.service.FoodsService;
import com.shop.demo.service.SellFoodsService;

@Controller
public class CoffeeController {
	
	@Autowired
	private FoodsService service;
	@Autowired
	private SellFoodsService sellService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/food/{id}")
	public String cat(@PathVariable("id")Integer id,Model model) {
		model.addAttribute("foodList", service.selectByCat(id));
		return "cat";
	}

	@GetMapping("/addNewFood")
	public String addNewFood(Model model) {
		model.addAttribute("food", new Foods());
		return "addNewFood";
	}
	
	@GetMapping("/deleteFood/{id}")
	public String deleteFood(@PathVariable("id")Integer id,Model model) {
		
		String dir = "./images/"+service.selectOne(id).getImg();
		Path imgPath = Paths.get(dir);
		
		try {
			Files.deleteIfExists(imgPath);
			service.delete(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("foodList", service.selectAll());
		return "foodList";
	}

	@GetMapping("/updateFood/{id}")
	public String updateFood(@PathVariable("id")Integer id,Model model) {
		model.addAttribute("food", service.selectOne(id));
		return "updateFood";
	}
	
	@PostMapping("/updateFood")
	public String updateFoo(@ModelAttribute("food") @Valid Foods food, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		
		if (result.hasErrors()) {
			model.addAttribute("food", food);
			return "updateFood";
		}
		food.setImg(service.selectOne(food.getId()).getImg());
		if (!fileName.isEmpty()) {
			String dir = "./images/";
			Path delPath = Paths.get(dir+food.getImg());
			Path newPath = Paths.get(dir);
			Path filePath = newPath.resolve(fileName);
			try {
				Files.deleteIfExists(delPath);
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				food.setImg("/images/"+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		service.save(food);
		model.addAttribute("foodList", service.selectAll());
		return "foodList";
	}
	
	@GetMapping("/foodList")
	public String foodList(Model model) {
		model.addAttribute("foodList", service.selectAll());
		return "foodList";
	}
	@PostMapping("/addNewFood")
	public String createNewFood(@ModelAttribute("food") @Valid Foods food, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file) {
		// Get file name
		String fileName = file.getOriginalFilename();

		if (result.hasErrors()) {
			model.addAttribute("food", food);
			if (fileName.isEmpty()) {
				model.addAttribute("errormes", "Please Chose an Image");
			}
			return "addNewFood";
		}
		if (fileName.isEmpty()) {
			model.addAttribute("food", food);
			model.addAttribute("errormes", "Please Chose an Image");
			return "addNewFood";
		}
		// Create path
		String dir = "./images/";
		Path path = Paths.get(dir);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Path filePath = path.resolve(fileName);
		// Save Image
		try {
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		food.setImg("/images/"+fileName);
		service.save(food);
		model.addAttribute("food", new Foods());
		return "addNewFood";
	}
	
	@GetMapping("/showAllCat")
	public String showAllCat() {
		return "showAllCat";
	}
	@GetMapping("/recordSell")
	public String recordSell(Model model) {
		List<Invoice> invList=new ArrayList<>();
		List<SellFoods> sellFoodList= sellService.selectAll();
		for (SellFoods sellFood:sellFoodList) {
			if(invList.size()==0) {
				Invoice inv=new Invoice();
				inv.setInvoice(sellFood.getInvoice());
				inv.setDate(sellFood.getDate());
				inv.setTotalAmount(sellFood.getFoods().getPrice()*sellFood.getCount());
				invList.add(inv);
			} else if(sellFood.getInvoice()==invList.get(invList.size()-1).getInvoice())	{
				Invoice inv=invList.get(invList.size()-1);
				invList.remove(invList.size()-1);
				inv.setTotalAmount(inv.getTotalAmount()+(sellFood.getFoods().getPrice()*sellFood.getCount()));
				invList.add(inv);
			}else{
				Invoice inv=new Invoice();
				inv.setInvoice(sellFood.getInvoice());
				inv.setDate(sellFood.getDate());
				inv.setTotalAmount(sellFood.getFoods().getPrice()*sellFood.getCount());
				invList.add(inv);
			}
		}
		model.addAttribute("invList", invList);
		return "recordSell";
	}
	@GetMapping("/viewDetail/{id}")
	public String viewDetail(@PathVariable("id")Integer id,Model model) {
		List<SellFoods> sellFoodList=sellService.selectByInvoice(id);
		int totalcount = 0;
		int totalprice = 0;
		for(SellFoods sellFoods:sellFoodList) {
			totalcount=totalcount+sellFoods.getCount();
			totalprice=totalprice+(sellFoods.getFoods().getPrice()*sellFoods.getCount());
		}
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("totalprice", totalprice);
		model.addAttribute("sellFoodList", sellFoodList);
		return "viewDetail";
	}
}
