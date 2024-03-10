package com.example.weather.service.parser.geocoder;

import com.example.weather.dto.PlaceWithCoordinatesDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GeocoderParser {

    public PlaceWithCoordinatesDto parse(String city, String country) throws InvalidPathException, IOException, NoSuchElementException {
        final List<PlaceWithCoordinatesDto> placeWithCoordinatesDtoList = parseCsvListToListOfMap();
        final PlaceWithCoordinatesDto targetMap = placeWithCoordinatesDtoList.stream().filter(p ->
                p.getCity().equals(city) && p.getCountry().equals(country)
        ).findFirst().orElseThrow(() ->
                new NoSuchElementException("Target city was not found")
        );
        return targetMap;
    }

    private static List<PlaceWithCoordinatesDto> parseCsvListToListOfMap() throws IOException {
        List<CSVRecord> parsed = parseFile();
        return mapObject(parsed);
    }

    private static List<PlaceWithCoordinatesDto> mapObject(List<CSVRecord> parsed) {
        final int total = parsed.size();
        final List<PlaceWithCoordinatesDto> placeWithCoordinatesDtoList = new ArrayList<PlaceWithCoordinatesDto>(total - 1);
        for (int i = 1; i < total; i++) {
            final CSVRecord record = parsed.get(i);
            final PlaceWithCoordinatesDto placeWithCoordinatesDto = new PlaceWithCoordinatesDto(
                    String.valueOf(record.get(0)),
                    String.valueOf(record.get(4)),
                    Float.parseFloat(record.get(2)),
                    Float.parseFloat(record.get(3))
            );
            placeWithCoordinatesDtoList.add(placeWithCoordinatesDto);
        }
        return placeWithCoordinatesDtoList;
    }

    private static List<CSVRecord> parseFile() throws InvalidPathException, IOException {
        final InputStream inputStream = Optional.ofNullable(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("worldcities.csv")
        ).orElseThrow(() ->
                new InvalidPathException("World Cities weren't parsed from INPUT_STREAM", "World Cities weren't parsed")
        );
        final List<CSVRecord> parsed = CSVParser.parse(inputStream, StandardCharsets.UTF_8, CSVFormat.DEFAULT).getRecords();
        return parsed;
    }
}
