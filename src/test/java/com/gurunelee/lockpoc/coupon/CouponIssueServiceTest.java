package com.gurunelee.lockpoc.coupon;

import com.gurunelee.lockpoc.coupon.entity.Coupon;
import com.gurunelee.lockpoc.coupon.entity.CouponRepository;
import com.gurunelee.lockpoc.coupon.entity.CouponUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class CouponIssueServiceTest {
@Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponUserRepository couponUserRepository;

    @Autowired
    CouponIssueService couponIssueService;

    CountDownLatch countDownLatch = new CountDownLatch(100);
    ExecutorService executor = Executors.newFixedThreadPool(100);

    @Test
    void issueWithNoLock() {
        // given
        var coupon = couponRepository.save(new Coupon("할인 이벤트", 100L));

        // when
        for (int i = 0; i < 10000; i++) {
            executor.execute(() -> {
                couponIssueService.issueWithNoLock(1L, coupon.getCouponKey());
                countDownLatch.countDown();
            });
        }

        countDownLatch.countDown();

        // then
        assertNotEquals(couponUserRepository.countByCoupon_CouponKey(coupon.getCouponKey()), 100);
        assertNotEquals(couponRepository.getCouponByCouponKey(coupon.getCouponKey()).getRemainedStock(), 0);
    }
}