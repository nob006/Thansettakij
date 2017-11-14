package com.gpk.thansettakij.model;

/**
 * Created by nobtingtong on 11/11/2017.
 */

public class BoothModel {

    private int id;
    private String booths_id;
    private String company_name;
    private String intro;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBooths_id() {
        return booths_id;
    }

    public void setBooths_id(String booths_id) {
        this.booths_id = booths_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
