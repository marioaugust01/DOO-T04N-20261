package com.climatempo;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClimaService {
    
    private static final String API_KEY = "K8TY3T4SKDXE5PDFYAVUA9YL8"; 
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public DadosClima buscarClima(String cidade) throws ClimaException {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new ClimaException("O nome da cidade não pode estar vazio!");
        }

        try {
            String encodedCity = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
            String url = BASE_URL + encodedCity + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(), DadosClima.class);
            } 
            else if (response.statusCode() == 400 || response.statusCode() == 404) {
                throw new ClimaException("Cidade não encontrada. Verifique o nome digitado.");
            } 
            else {
                throw new ClimaException("Erro na API. Código HTTP: " + response.statusCode());
            }

        } catch (ClimaException e) {
            throw e;
        } catch (Exception e) {
            throw new ClimaException("Erro de conexão: " + e.getMessage());
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DadosClima {
        public CurrentConditions currentConditions;
        public List<Day> days;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentConditions {
        public double temp;
        public double humidity;
        public String conditions;
        public double precip;
        public double windspeed;
        public double winddir;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Day {
        public double tempmax;
        public double tempmin;
    }
}