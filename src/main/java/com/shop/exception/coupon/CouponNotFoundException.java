package com.shop.exception.coupon;

public class CouponNotFoundException extends RuntimeException{
    public CouponNotFoundException(String message){
        super(message);
    }

}
