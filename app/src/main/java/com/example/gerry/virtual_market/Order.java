package com.example.gerry.virtual_market;

/**
 * Created by Gerry on 4/13/2017.
 */

public class Order {
    Integer customerName;
    Integer totalProducts;
    Integer totalPrices;

    public String getCustomerName(){
        return Integer.toString(customerName);
    }

    public void setCustomerName(Integer customerName){
        this.customerName = customerName;
    }

    public String getTotalProducts(){
        return Integer.toString(totalProducts);
    }

    public void setTotalProducts(Integer totalProducts){
        this.totalProducts = totalProducts;
    }

    public String getTotalPrices(){
        return Integer.toString(totalPrices);
    }

    public void setTotalPrices(Integer totalPrices){
        this.totalPrices = totalPrices;
    }

}
