package com.nivas.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nivas.productservice.model.Product;
import com.nivas.productservice.repository.ProductRepo;

@Controller
public class ProductController {

	@Autowired
	ProductRepo repo;

	@GetMapping("/showCreateProduct")
	public String showCreateProduct() {
		return "createProduct";
	}

	@PostMapping("/saveProduct")
	public String save(Product product) {
		repo.save(product);
		return "createResponse";
	}

	@GetMapping("/showGetProduct")
	public String showGetProduct() {
		return "getProduct";
	}

	@PostMapping("/getProduct")
	public ModelAndView getProduct(String name) {
		ModelAndView mav = new ModelAndView("productDetails");
		System.out.println(name);
		mav.addObject(repo.findByName((name)));
		return mav;
	}

}
