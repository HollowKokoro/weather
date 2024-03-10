package com.example.weather.service.parser;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import com.example.weather.service.parser.api.impl.OpenMeteoParser;
import com.example.weather.service.parser.api.impl.OpenWeatherParser;
import com.example.weather.service.parser.api.impl.VisualCrossingParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParserTest {

    @Value("${visualCrossingKey}")
    private String visualCrossingKey;
    @Value("${openWeatherKey}")
    private String openWeatherKey;

    @Test
    public void InputPlacesList_Check_City_Country() {
        String place = "Moscow,Russia";
        final int comaPosition = place.indexOf(',');
        final String city = place.substring(0, comaPosition).trim();
        final String country = place.substring(comaPosition + 1).trim();
        assertEquals(city, "Moscow");
        assertEquals(country, "Russia");
    }

    @Test
    public void TemperatureList_Get_Average() throws FileNotFoundException {
        PlaceWithCoordinatesDto place = new PlaceWithCoordinatesDto("Moscow", "Russia", 0F, 0F);
        ArrayList<Double> temperatureList = new ArrayList<Double>(3);
        OpenMeteoParser openMeteoParserMock = mock(OpenMeteoParser.class);
        when(openMeteoParserMock.parse(place)).thenReturn(1D);
        VisualCrossingParser visualCrossingParserMock = mock(VisualCrossingParser.class);
        when(visualCrossingParserMock.parse(place)).thenReturn(2D);
        OpenWeatherParser openWeatherParserMock = mock(OpenWeatherParser.class);
        when(openWeatherParserMock.parse(place)).thenReturn(3D);
        temperatureList.add(openMeteoParserMock.parse(place));
        temperatureList.add(visualCrossingParserMock.parse(place));
        temperatureList.add(openWeatherParserMock.parse(place));
        OptionalDouble averageTemperature = temperatureList.stream()
                .mapToDouble(a -> a)
                .average();
        double averageTemperatureAsDouble = averageTemperature.getAsDouble();
        assertEquals(averageTemperatureAsDouble, 2D);
    }
}
