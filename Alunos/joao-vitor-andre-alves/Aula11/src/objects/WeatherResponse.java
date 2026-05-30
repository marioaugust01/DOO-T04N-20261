package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherResponse {

    private final int statusCode;
    private final String body;
    private final String url;
    private final String resolvedAddress;
    private final String timezone;
    private final CurrentConditions currentConditions;
    private final List<WeatherDay> days;

    public WeatherResponse(int statusCode, String body, String url) {
        this.statusCode = statusCode;
        this.body = body;
        this.url = url;
        this.resolvedAddress = extrairTexto(body, "resolvedAddress");
        this.timezone = extrairTexto(body, "timezone");
        this.currentConditions = montarCondicoesAtuais(body);
        this.days = montarDias(body);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public String getTimezone() {
        return timezone;
    }

    public CurrentConditions getCurrentConditions() {
        return currentConditions;
    }

    public List<WeatherDay> getDays() {
        return days;
    }

    public boolean isSucesso() {
        return statusCode >= 200 && statusCode < 300;
    }

    public static CurrentConditions montarCondicoesAtuais(String json) {
        String objeto = extrairObjeto(json, "currentConditions");

        if (objeto == null) {
            return null;
        }

        return new CurrentConditions(
                extrairTexto(objeto, "datetime"),
                extrairNumero(objeto, "temp"),
                extrairNumero(objeto, "humidity"),
                extrairTexto(objeto, "conditions"),
                extrairNumero(objeto, "windspeed"),
                extrairNumero(objeto, "winddir"),
                extrairNumero(objeto, "precip")
        );
    }

    private static List<WeatherDay> montarDias(String json) {
        String array = extrairArray(json, "days");
        if (array == null) {
            return new ArrayList<>();
        }

        List<WeatherDay> dias = new ArrayList<>();
        for (String obj : separarObjetos(array)) {
            dias.add(new WeatherDay(
                    extrairTexto(obj, "datetime"),
                    extrairNumero(obj, "tempmax"),
                    extrairNumero(obj, "tempmin")
            ));
        }
        return dias;
    }

    private static String extrairTexto(String json, String campo) {
        Matcher matcher = Pattern
                .compile("\"" + Pattern.quote(campo) + "\"\\s*:\\s*\"(.*?)\"")
                .matcher(json);

        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }

    private static Double extrairNumero(String json, String campo) {
        Matcher matcher = Pattern
                .compile("\"" + Pattern.quote(campo) + "\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)")
                .matcher(json);

        if (!matcher.find()) {
            return null;
        }

        return Double.parseDouble(matcher.group(1));
    }

    private static String extrairObjeto(String json, String campo) {
        int inicioCampo = json.indexOf("\"" + campo + "\"");

        if (inicioCampo < 0) {
            return null;
        }

        int inicioObjeto = json.indexOf('{', inicioCampo);
        return extrairBloco(json, inicioObjeto, '{', '}');
    }

    private static String extrairArray(String json, String campo) {
        int inicioCampo = json.indexOf("\"" + campo + "\"");

        if (inicioCampo < 0) {
            return null;
        }

        int inicioArray = json.indexOf('[', inicioCampo);
        return extrairBloco(json, inicioArray, '[', ']');
    }

    private static String extrairBloco(String texto, int inicio, char abertura, char fechamento) {
        if (inicio < 0) {
            return null;
        }

        int nivel = 0;

        for (int i = inicio; i < texto.length(); i++) {
            char atual = texto.charAt(i);

            if (atual == abertura) {
                nivel++;
            } else if (atual == fechamento) {
                nivel--;

                if (nivel == 0) {
                    return texto.substring(inicio, i + 1);
                }
            }
        }

        return null;
    }

    private static List<String> separarObjetos(String array) {
        List<String> objetos = new ArrayList<>();
        int inicio = -1;
        int nivel = 0;

        for (int i = 0; i < array.length(); i++) {
            char atual = array.charAt(i);

            if (atual == '{') {
                if (nivel == 0) {
                    inicio = i;
                }

                nivel++;
            } else if (atual == '}') {
                nivel--;

                if (nivel == 0 && inicio >= 0) {
                    objetos.add(array.substring(inicio, i + 1));
                    inicio = -1;
                }
            }
        }

        return objetos;
    }
}
