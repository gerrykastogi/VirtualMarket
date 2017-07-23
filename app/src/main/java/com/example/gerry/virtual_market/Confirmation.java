package com.example.gerry.virtual_market;

/**
 * Created by Gerry on 6/16/2017.
 */

public class Confirmation {
    String customerName;
    String customerAddress;
    String customerPhone;
    Integer totalPrice;
    Integer rates;

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public String getCustomerAddress(){
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress){
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone(){
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone){
        this.customerPhone = customerPhone;
    }

    public String getTotalPrice(){
        return Integer.toString(totalPrice);
    }

    public void setTotalPrice(Integer totalPrice){
        this.totalPrice = totalPrice;
    }

    public String getRates(){
        return Integer.toString(rates);
    }

    public void setRates(Integer rates){
        this.rates = rates;
    }
}
