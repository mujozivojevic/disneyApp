package com.codecta.academy.services;

import com.codecta.academy.services.integration.WeatherData;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@RegisterRestClient(configKey = "weather-api")
@Path("/2.5")
public interface WeatherService {
    @GET
    @Path("/weather")
    WeatherData getWeather(@QueryParam("q") String city, @QueryParam("appid") String apiKey, @QueryParam("units") String units);
}
