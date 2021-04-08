package com.codecta.academy.services.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

    public static class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public static class Coord{
        public double lon;
        public double lat;
    }

    public static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Main{
        public double temp;
        public double feels_like;
        public int temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }

    public static class Wind{
        public double speed;
        public int deg;
    }

    public static class Clouds{
        public int all;
    }

}

