package objects;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public class WeatherService {

    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private final String apiKey;
    private final HttpClient httpClient;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherResponse consultar(WeatherRequest request) {
        String url = montarUrl(request);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            return new WeatherResponse(
                    httpResponse.statusCode(),
                    httpResponse.body(),
                    url);
        } catch (IOException e) {
            return new WeatherResponse(
                    0,
                    "Erro ao consultar a API: " + e.getMessage(),
                    url);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new WeatherResponse(
                    0,
                    "Consulta interrompida.",
                    url);
        }
    }

    public String montarUrl(WeatherRequest request) {
        StringBuilder url = new StringBuilder(BASE_URL);

        url.append(encode(request.getLocation()));

        if (request.getInit() != null && !request.getInit().isBlank()) {
            url.append("/").append(encode(request.getInit()));
        }

        if (request.getFim() != null && !request.getFim().isBlank()) {
            url.append("/").append(encode(request.getFim()));
        }

        url.append("?key=").append(encode(apiKey));

        if (request.getUnitGroup() != null) {
            url.append("&unitGroup=").append(encode(request.getUnitGroup()));
        }

        if (request.getLang() != null) {
            url.append("&lang=").append(encode(request.getLang()));
        }

        if (request.getInclude() != null && !request.getInclude().isEmpty()) {
            url.append("&include=").append(join(request.getInclude()));
        }

        if (request.getElements() != null && !request.getElements().isEmpty()) {
            url.append("&elements=").append(join(request.getElements()));
        }

        if (request.getContentType() != null) {
            url.append("&contentType=").append(encode(request.getContentType()));
        }

        return url.toString();
    }

    public String join(java.util.List<String> values) {
        StringJoiner joiner = new StringJoiner(",");
        for (String value : values) {
            joiner.add(value);
        }
        return encode(joiner.toString());
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
