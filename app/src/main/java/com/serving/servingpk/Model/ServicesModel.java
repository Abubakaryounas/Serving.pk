package com.serving.servingpk.Model;


public class ServicesModel {
    String mscategory_name, mscategory_id,display_order;
    String service_logo;

    public ServicesModel(String mscategory_name, String service_logo,String mscategory_id,String display_order) {

        this.mscategory_name = mscategory_name;

        this.service_logo = service_logo;
        this.mscategory_id=mscategory_id;
        this.display_order=display_order;
    }

    public String getMscategory_name() {
        return mscategory_name;
    }

    public void setMscategory_name(String mscategory_name) {
        this.mscategory_name = mscategory_name;
    }

    public String getMscategory_id() {
        return mscategory_id;
    }

    public void setMscategory_id(String mscategory_id) {
        this.mscategory_id = mscategory_id;
    }

    public String getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(String display_order) {
        this.display_order = display_order;
    }

    public String getService_logo() {
        return service_logo;
    }

    public void setService_logo(String service_logo) {
        this.service_logo = service_logo;
    }
}
