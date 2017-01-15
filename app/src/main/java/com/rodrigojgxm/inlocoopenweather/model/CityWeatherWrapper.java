package com.rodrigojgxm.inlocoopenweather.model;

import java.io.Serializable;

/**
 * Created by Rodrigojgxm on 1/15/2017.
 */

public class CityWeatherWrapper implements Serializable {
    private String mName;
    private Double mMaxTemp;
    private Double mMinTemp;
    private String mDescription;


    public CityWeatherWrapper() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Double getMaxTemp() {
        return mMaxTemp;
    }

    public void setMaxTemp(Double mMaxTemp) {
        this.mMaxTemp = mMaxTemp;
    }

    public Double getMinTemp() {
        return mMinTemp;
    }

    public void setMinTemp(Double mMinTemp) {
        this.mMinTemp = mMinTemp;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
