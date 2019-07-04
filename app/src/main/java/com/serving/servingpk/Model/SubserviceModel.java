package com.serving.servingpk.Model;

public class SubserviceModel {
    public String getSscategory_id() {
        return sscategory_id;
    }

    public void setSscategory_id(String sscategory_id) {
        this.sscategory_id = sscategory_id;
    }

    public String getSscategory_name() {
        return sscategory_name;
    }

    public void setSscategory_name(String sscategory_name) {
        this.sscategory_name = sscategory_name;
    }

    public String getSservice_logo() {
        return sservice_logo;
    }

    public void setSservice_logo(String sservice_logo) {
        this.sservice_logo = sservice_logo;
    }

    String sscategory_id;
    String sscategory_name;

    public SubserviceModel(String sscategory_id, String sscategory_name, String sservice_logo) {
        this.sscategory_id = sscategory_id;
        this.sscategory_name = sscategory_name;
        this.sservice_logo = sservice_logo;
    }

    String sservice_logo;
}
