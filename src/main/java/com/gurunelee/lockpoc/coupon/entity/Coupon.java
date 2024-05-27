package com.gurunelee.lockpoc.coupon.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "app_coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_key")
    private Long couponKey = 0L;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "remained_stock", nullable = false)
    Long remainedStock;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "coupon")
    Set<CouponUser> couponUsers = Set.of();

    public Coupon() {
    }

    public Coupon(String name, Long remainedStock) {
        this.name = name;
        this.remainedStock = remainedStock;
    }

    public Long getId() {
        return this.couponKey;
    }

    public Set<CouponUser> getCouponUsers() {
        return couponUsers;
    }

    public void issue(Long userKey) {
        if (remainedStock < 1) {
            return;
        }

        CouponUser couponUser = new CouponUser(this, userKey);
        if (this.couponUsers.contains(couponUser)) {
            return;
        }

        this.couponUsers.add(couponUser);
        this.remainedStock --;
    }

    public Long getCouponKey() {
        return this.couponKey;
    }

    public Long getRemainedStock() {
        return this.remainedStock;
    }
}
