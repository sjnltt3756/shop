package com.shop.exception.wishlist;

public class WishListNotFoundException extends RuntimeException{
    public WishListNotFoundException(String message){
        super(message);
    }
}
