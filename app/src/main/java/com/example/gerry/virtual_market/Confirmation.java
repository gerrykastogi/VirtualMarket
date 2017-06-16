package com.example.gerry.virtual_market;

/**
 * Created by Gerry on 6/16/2017.
 */

public class Confirmation {
    String customerName;
    String customerAddress;
    String customerPhone;

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
}
