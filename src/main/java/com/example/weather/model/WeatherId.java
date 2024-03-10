package com.example.weather.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class WeatherId implements Serializable {
    private float latitude;
    private float longitude;

    public WeatherId(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WeatherId() {
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherId weatherId = (WeatherId) o;
        return Float.compare(latitude, weatherId.latitude) == 0 && Float.compare(longitude, weatherId.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
