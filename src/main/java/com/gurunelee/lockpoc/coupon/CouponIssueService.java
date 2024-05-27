package com.gurunelee.lockpoc.coupon;

import com.gurunelee.lockpoc.coupon.entity.Coupon;
import com.gurunelee.lockpoc.coupon.entity.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponIssueService {
    private final CouponRepository couponRepository;

    public CouponIssueService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void issueWithNoLock(Long userKey, Long couponKey) {
        Coupon coupon = couponRepository.getCouponByCouponKey(couponKey);
        coupon.issue(userKey);
        couponRepository.save(coupon);
    }
}
