package com.example.weather.service.parser.api.impl;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import com.example.weather.service.parser.api.ApiParser;
import com.google.gson.JsonObject;

public class OpenMeteoParser extends ApiParser {

    private static final String URL_PATTERN = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m";

    @Override
    protected double getTemperature(JsonObject jsonObject) {
        return jsonObject.getAsJsonObject("current").get("temperature_2m").getAsDouble();
    }

    @Override
    protected String buildUrl(PlaceWithCoordinatesDto placeWithCoordinatesDto) {
        return String.format(
                URL_PATTERN,
                placeWithCoordinatesDto.getLatitude(),
                placeWithCoordinatesDto.getLongitude()
        );
    }
}
