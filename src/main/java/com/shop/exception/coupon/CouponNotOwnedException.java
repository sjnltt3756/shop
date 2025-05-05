package com.shop.exception.coupon;

public class CouponNotOwnedException extends RuntimeException{
    public CouponNotOwnedException(String message){
        super(message);
    }

}
