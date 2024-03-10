package com.example.weather.service.parser.api.impl;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import com.example.weather.service.parser.api.ApiParser;
import com.google.gson.JsonObject;

public class OpenWeatherParser extends ApiParser {

    final String apiKey;

    public OpenWeatherParser(String apiKey) {
        this.apiKey = apiKey;
    }

    private static final String URL_PATTERN = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

    @Override
    protected double getTemperature(JsonObject jsonObject) {
        return jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
    }

    @Override
    protected String buildUrl(PlaceWithCoordinatesDto placeWithCoordinatesDto) {
        return String.format(
                URL_PATTERN,
                placeWithCoordinatesDto.getLatitude(),
                placeWithCoordinatesDto.getLongitude(),
                apiKey
        );
    }
}
