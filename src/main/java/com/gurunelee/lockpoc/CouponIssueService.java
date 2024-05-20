package com.gurunelee.lockpoc;

import com.gurunelee.lockpoc.entity.Coupon;
import com.gurunelee.lockpoc.entity.CouponRepository;
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
