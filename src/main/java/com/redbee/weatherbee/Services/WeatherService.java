package com.redbee.weatherbee.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.weatherbee.Entities.Condition;
import com.redbee.weatherbee.Entities.Forecast;
import com.redbee.weatherbee.Entities.Location;
import com.redbee.weatherbee.Repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WeatherService {

    private static final String yahooURL = "http://query.yahooapis.com/v1/public/yql";

    @Autowired
    private ForecastRepository forecastRepository;

    private String buildUpdateLocationsQuery(List<String> locationNames) {
        locationNames.replaceAll( name -> "'" + name + "'");
        System.out.println(locationNames);
        String cities = String.join(",", locationNames);
        String query = "select location, item from weather.forecast where woeid in (select woeid from geo.places(1) where text in (" + cities + ")) and u='c'";
        return query;
    }

    public Set<Location> getUpdatedLocations(List<String> locationNames) {
        String query = buildUpdateLocationsQuery(locationNames);
        try {
            String yahooResponse = callYahooApi(query);
            return yahooJsonLocationParser(yahooResponse);
        } catch (ResourceAccessException raEx) {
            System.out.println("No se pudo establecer la conexion con el servicio de yahoo. Chequear conectividad.");
            return null;
        }
    }

    private String callYahooApi(String query) throws ResourceAccessException{
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(yahooURL)
                .queryParam("q", query)
                .queryParam("format", "json");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        System.out.println("Calling yahoo with query: " + query);

        HttpEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }

    private Set<Location> yahooJsonLocationParser(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Set<Location> locations = new HashSet<>();
        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode channelNode = rootNode.findValue("channel");
            for (JsonNode channelItem : channelNode) {
                String cityName = channelItem.get("location").get("city").asText();
                JsonNode conditionNode = channelItem.get("item").get("condition");
                Condition condition = mapper.treeToValue(conditionNode, Condition.class);
                JsonNode forecastNode = channelItem.get("item").get("forecast");
                Set<Forecast> forecasts = new HashSet<>();
                for (JsonNode forecastItem : forecastNode) {
                    Forecast forecast = mapper.treeToValue(forecastItem, Forecast.class);
                    //forecast = forecastRepository.save(forecast);
                    forecasts.add(forecast);
                }
                Location loc = new Location(cityName);
                loc.setCondition(condition);
                loc.setForecasts(forecasts);
                locations.add(loc);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        return locations;
    }
}

