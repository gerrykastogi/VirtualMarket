package com.example.gerry.virtual_market;

/**
 * Created by Gerry on 4/13/2017.
 */

public class OrderLine {
    String productName;
    Integer quantity;
    Integer productPrice;
    Integer imageResourceId;
    Integer isfav;
    Integer isturned;

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getQuantity(){
        return Integer.toString(quantity);
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public String getProductPrice(){
        return Integer.toString(productPrice);
    }

    public void setProductPrice(Integer productPrice){
        this.productPrice = productPrice;
    }

    public Integer getImageResourceId(){
        return imageResourceId;
    }

    public void setImageResourceId(Integer imageResourceId){
        this.imageResourceId = imageResourceId;
    }

    public Integer getIsturned(){
        return isturned;
    }

    public void setIsturned(Integer isturned){
        this.isturned = isturned;
    }

    public Integer getIsfav(){
        return isfav;
    }

    public void setIsfav(Integer isfav){
        this.isfav = isfav;
    }
}