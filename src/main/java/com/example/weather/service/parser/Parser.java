package com.example.weather.service.parser;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.parser.api.impl.OpenMeteoParser;
import com.example.weather.service.parser.api.impl.OpenWeatherParser;
import com.example.weather.service.parser.api.impl.VisualCrossingParser;
import com.example.weather.service.parser.geocoder.GeocoderParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@Transactional
public class Parser {

    @Value("${inputPlaces}")
    private String inputPlacesStr;
    @Value("${visualCrossingKey}")
    private String visualCrossingKey;
    @Value("${openWeatherKey}")
    private String openWeatherKey;

    @Autowired
    private WeatherRepository weatherRepository;

    @Scheduled(fixedDelayString = "${interval}")
    public void parse() {
        final List<String> inputPlaces = Arrays.asList(inputPlacesStr.split(";"));
        final List<PlaceWithCoordinatesDto> placeList = inputPlaces.stream().map(p -> {
            final int comaPosition = p.indexOf(',');
            final String city = p.substring(0, comaPosition).trim();
            final String country = p.substring(comaPosition + 1).trim();
            PlaceWithCoordinatesDto placeWithCoordinatesDto;
            try {
                placeWithCoordinatesDto = new GeocoderParser().parse(city, country);
            } catch (IOException e) {
                placeWithCoordinatesDto = null;
            }
            return placeWithCoordinatesDto;
        }).collect(Collectors.toList());
        placeList.forEach((PlaceWithCoordinatesDto place) -> {
            ArrayList<Double> temperatureList = new ArrayList<Double>(3);
            try {
                temperatureList.add(new OpenMeteoParser().parse(place));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                temperatureList.add(new VisualCrossingParser(visualCrossingKey).parse(place));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                temperatureList.add(new OpenWeatherParser(openWeatherKey).parse(place));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            OptionalDouble averageTemperature = temperatureList.stream()
                    .mapToDouble(a -> a)
                    .average();
            double averageTemperatureAsDouble = averageTemperature.getAsDouble();
            Weather weather = new Weather(place.getCity(), place.getCountry(), place.getLatitude(), place.getLongitude(), averageTemperatureAsDouble);
            weatherRepository.upsert(weather);
        });
    }
}
