package com.gurunelee.lockpoc.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "app_coupon_user")
public class CouponUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_user_key")
    Long couponUserKey = 0L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_key", nullable = false, updatable = false)
    Coupon coupon;

    @Column(name = "user_key", nullable = false, updatable = false)
    Long userKey;

    public CouponUser(Coupon coupon, Long userKey) {
        this.coupon = coupon;
        this.userKey = userKey;
    }

    public CouponUser() {

    }

    public Long getId() {
        return this.couponUserKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponUser that)) return false;

        if (coupon != ((CouponUser) o).coupon) return false;

        return Objects.equals(couponUserKey, that.couponUserKey) && Objects.equals(coupon, that.coupon) && Objects.equals(userKey, that.userKey);
    }

    @Override
    public int hashCode() {
        int result = coupon.hashCode();
        result = 31 * result + userKey.hashCode();

        return result;
    }
}

