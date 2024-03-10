package com.example.weather.service.parser.api;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;

import static com.example.weather.service.parser.utils.Downloader.downloadPage;

public abstract class ApiParser {
    protected abstract double getTemperature(JsonObject jsonObject);

    protected abstract String buildUrl(PlaceWithCoordinatesDto placeWithCoordinatesDto);

    public double parse(PlaceWithCoordinatesDto placeWithCoordinatesDto) throws FileNotFoundException {
        final String url = buildUrl(placeWithCoordinatesDto);
        final JsonElement jsonElement = JsonParser.parseString(downloadPage(url));
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        return getTemperature(jsonObject);
    }
}
