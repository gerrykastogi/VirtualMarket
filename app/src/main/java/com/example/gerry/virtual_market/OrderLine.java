package com.example.gerry.virtual_market;

/**
 * Created by Gerry on 4/13/2017.
 */

public class OrderLine {
    Integer orderLineId;
    String productName;
    Integer quantity;
    Integer productPrice;
    String imageUrl;

    public String getOrderLineId(){
        return Integer.toString(orderLineId);
    }

    public void setOrderLineId(Integer orderLineId){
        this.orderLineId = orderLineId;
    }

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

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
