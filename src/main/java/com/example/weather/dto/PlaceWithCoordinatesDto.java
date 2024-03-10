package com.example.weather.dto;

public class PlaceWithCoordinatesDto {
    private final String city;
    private final String country;
    private final float latitude;
    private final float longitude;

    public PlaceWithCoordinatesDto(String city, String country, float latitude, float longitude) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
