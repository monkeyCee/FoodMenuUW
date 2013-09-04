package ca.uwaterloo.uwfoodservices;

import java.io.Serializable;

public class WatcardVendorObject implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3065187774825856269L;
    private String watcard_vendor_name;
    private String location;
    private String telephone;

    public WatcardVendorObject(String watcard_vendor_name, String location, String telephone){
        this.watcard_vendor_name = watcard_vendor_name;
        this.location = location;
        this.telephone = telephone;
    }

    public String getVendorName(){
        return this.watcard_vendor_name;
    }

    public String getLocation(){
        return this.location;
    }

    public String getTelephone(){
        return this.telephone;
    }

}
