package com.nivas.productservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nivas.productservice.dto.Coupon;
import com.nivas.productservice.model.Product;
import com.nivas.productservice.repository.ProductRepo;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${couponService.url}")
	private String couponServiceURL;
	
	@PostMapping("/product")
	public Product create(@RequestBody Product product) {
		Coupon coupon =  restTemplate.getForObject(couponServiceURL+product.getCouponCode(), Coupon.class);
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return productRepo.save(product);
	}
	
	@GetMapping("/product/{id}")
	public Optional<Product> getProduct(@PathVariable Long id) {
		return productRepo.findById(id);
		
	}
	
	@GetMapping("/product/all")
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public Product sendErrorResponse(Product product) {
		return product;
	}

}
