package com.example.weather.service.parser.api.impl;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import com.example.weather.service.parser.api.ApiParser;
import com.google.gson.JsonObject;

public class VisualCrossingParser extends ApiParser {

    final String apiKey;

    public VisualCrossingParser(String apiKey) {
        this.apiKey = apiKey;
    }

    private static final String URL_PATTERN = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s,%s?key=%s&unitGroup=metric";

    @Override
    protected double getTemperature(JsonObject jsonObject) {
        return jsonObject.getAsJsonArray("days").get(0).getAsJsonObject().get("temp").getAsDouble();
    }

    @Override
    protected String buildUrl(PlaceWithCoordinatesDto placeWithCoordinatesDto) {
        return String.format(
                URL_PATTERN,
                placeWithCoordinatesDto.getCity(),
                placeWithCoordinatesDto.getCountry(),
                apiKey
        );
    }
}
