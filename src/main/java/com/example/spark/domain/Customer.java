package com.example.spark.domain;

import javax.persistence.*;

public class Customer {

    private Long id;
    private String demographicRegion;
    private int ageGroup;
    private int daysSinceLastOrder;
    private String membership;
    private String shopType;
    private int y2013;
    private int y2014;
    private int y2015;
    private int y2016;
    private int y2017;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemographicRegion() {
        return demographicRegion;
    }

    public void setDemographicRegion(String demographicRegion) {
        this.demographicRegion = demographicRegion;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public int getDaysSinceLastOrder() {
        return daysSinceLastOrder;
    }

    public void setDaysSinceLastOrder(int daysSinceLastOrder) {
        this.daysSinceLastOrder = daysSinceLastOrder;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public int getY2013() {
        return y2013;
    }

    public void setY2013(int y2013) {
        this.y2013 = y2013;
    }

    public int getY2014() {
        return y2014;
    }

    public void setY2014(int y2014) {
        this.y2014 = y2014;
    }

    public int getY2015() {
        return y2015;
    }

    public void setY2015(int y2015) {
        this.y2015 = y2015;
    }

    public int getY2016() {
        return y2016;
    }

    public void setY2016(int y2016) {
        this.y2016 = y2016;
    }

    public int getY2017() {
        return y2017;
    }

    public void setY2017(int y2017) {
        this.y2017 = y2017;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "id:" + "'" + id + "'" +
                ", demographicRegion:" + "'" + demographicRegion + "'" +
                ", ageGroup:" + "'" + ageGroup + "'" +
                ", daysSinceLastOrder:" + "'" + daysSinceLastOrder + "'" +
                ", membership:" + "'" + membership + "'" +
                ", shopType:" + "'" + shopType + "'" +
                ", y2013:" + "'" + y2013 + "'" +
                ", y2014:" + "'" + y2014 + "'" +
                ", y2015:" + "'" + y2015 + "'" +
                ", y2016:" + "'" + y2016 + "'" +
                ", y2017:" + "'" + y2017 + "'" +
                '}';
    }
}
