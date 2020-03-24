package com.rohankadkol.activityweatherapp.pojos;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
    private Main main;
    private List<Weather> weatherList;

    public WeatherResponse(double temp, double tempMin, double tempMax, String main, String description) {
        this.main = new Main(temp, tempMin, tempMax);
        this.weatherList = new ArrayList<>();
        this.weatherList.add(new Weather(main, description));
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }
}
