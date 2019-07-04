package com.serving.servingpk.Model;

public class ServiceType {


    String stype_id="";
     String stype_name="";
     String display_order;

    public ServiceType(String stype_id, String stype_name, String display_order) {
        this.stype_id = stype_id;
        this.stype_name = stype_name;
        this.display_order = display_order;
    }

    public String getStype_id() {
        return stype_id;
    }

    public void setStype_id(String stype_id) {
        this.stype_id = stype_id;
    }

    public String getStype_name() {
        return stype_name;
    }

    public void setStype_name(String stype_name) {
        this.stype_name = stype_name;
    }

    public String getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(String display_order) {
        this.display_order = display_order;
    }
}
