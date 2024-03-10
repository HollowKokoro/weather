package com.example.weather.repository;

import com.example.weather.model.Weather;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class WeatherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void upsert(Weather weather) {
        entityManager.createNativeQuery("MERGE INTO weather KEY (latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?)")
                .setParameter(1, weather.getLatitude())
                .setParameter(2, weather.getLongitude())
                .setParameter(3, weather.getCity())
                .setParameter(4, weather.getCountry())
                .setParameter(5, weather.getCreationTimestamp())
                .setParameter(6, weather.getTemperature())
                .setParameter(7, weather.getUpdateTimestamp())
                .executeUpdate();
    }

    @Transactional
    public List<Weather> getAll() {
        return entityManager.createNativeQuery("SELECT * FROM weather").getResultList();
    }
}

