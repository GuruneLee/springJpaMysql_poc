package com.gurunelee.lockpoc.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {
    CouponUser getCouponUserByCouponUserKey(Long couponUserKey);

    Long countByCoupon_CouponKey(Long couponKey);
}
