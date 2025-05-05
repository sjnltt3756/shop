package com.shop.exception.coupon;

public class CouponExpiredException extends RuntimeException{
    public CouponExpiredException(String message){
        super(message);
    }

}
