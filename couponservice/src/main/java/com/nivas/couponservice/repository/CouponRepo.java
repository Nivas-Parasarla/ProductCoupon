package com.nivas.couponservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivas.couponservice.model.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long>{

	Coupon findByCode(String code);

}
