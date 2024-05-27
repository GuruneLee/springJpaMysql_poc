package com.gurunelee.lockpoc.coupon.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon getCouponByCouponKey(Long couponKey);
}
