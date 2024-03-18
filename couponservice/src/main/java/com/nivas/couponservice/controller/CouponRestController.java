package com.nivas.couponservice.controller;

import java.util.List;

import org.hibernate.event.spi.PostCollectionUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nivas.couponservice.model.Coupon;
import com.nivas.couponservice.repository.CouponRepo;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {
	@Autowired
	CouponRepo couponRepo;
	
	@PostMapping("/coupons")
	public Coupon create(@RequestBody Coupon coupon) {
		return couponRepo.save(coupon);
	}
	
	@RequestMapping(value="/coupons/{code}",method = RequestMethod.GET)
	public Coupon getCoupon(@PathVariable("code") String code) {
		return couponRepo.findByCode(code);
	}
	
	@GetMapping("/coupons/all")
	public List<Coupon> getAllCoupons() {
		return couponRepo.findAll();
	}
}
