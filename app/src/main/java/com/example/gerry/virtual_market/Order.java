package com.example.gerry.virtual_market;

/**
 * Created by Gerry on 4/13/2017.
 */

public class Order {
    String customerName;
    Integer orderId;
    Integer totalProduct;
    Integer totalPrice;

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    public String getTotalProducts(){
        return Integer.toString(totalProduct);
    }

    public void setTotalProducts(Integer totalProducts){
        this.totalProduct = totalProducts;
    }

    public String getTotalPrices(){
        return Integer.toString(totalPrice);
    }

    public void setTotalPrices(Integer totalPrices){
        this.totalPrice = totalPrices;
    }

}
