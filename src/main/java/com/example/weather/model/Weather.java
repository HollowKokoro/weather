package com.example.weather.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "weather")
@IdClass(WeatherId.class)
public class Weather implements Serializable {

    @Id
    private Float latitude;
    @Id
    private Float longitude;
    private String city;
    private String country;
    private Double temperature;
    @CreationTimestamp
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;

    public Weather(String city, String country, Float latitude, Float longitude, Double temperature) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
    }

    public Weather() {
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", temperature=" + temperature +
                ", creationTimestamp=" + creationTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Float.compare(latitude, weather.latitude) == 0 && Float.compare(longitude, weather.longitude) == 0 && Double.compare(temperature, weather.temperature) == 0 && Objects.equals(city, weather.city) && Objects.equals(country, weather.country) && Objects.equals(creationTimestamp, weather.creationTimestamp) && Objects.equals(updateTimestamp, weather.updateTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, latitude, longitude, temperature, creationTimestamp, updateTimestamp);
    }
}
