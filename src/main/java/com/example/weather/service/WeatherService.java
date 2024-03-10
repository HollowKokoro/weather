package com.example.weather.service;

import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class WeatherService {

    @Autowired
    private Parser parser;

    @Autowired
    private WeatherRepository weatherRepository;

    public List<Weather> getAll() throws IOException {
        List<Weather> weatherList = weatherRepository.getAll();
        if (weatherList.isEmpty()) {
            parser.parse();
            return weatherRepository.getAll();
        }
        return weatherList;
    }
}
