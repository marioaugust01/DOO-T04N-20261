package objects;

import java.util.List;

public class WeatherRequest {

    private String location;
    private String dateInit;
    private String dateFim;
    private String unitGroup;
    private String lang;
    private List<String> include;
    private List<String> elements;
    private String contentType;

    public WeatherRequest() {

    }

    public WeatherRequest(
            String location,
            String date1,
            String date2,
            String unitGroup,
            String lang,
            List<String> include,
            List<String> elements,
            String contentType) {
        this.location = location;
        this.dateInit = date1;
        this.dateFim = date2;
        this.unitGroup = unitGroup;
        this.lang = lang;
        this.include = include;
        this.elements = elements;
        this.contentType = contentType;
    }

    public static WeatherRequest climaAtual(String location) {
        return new WeatherRequest(
                location,
                "today",
                null,
                "metric",
                "pt",
                List.of("current", "days"),
                List.of("datetime", "temp", "tempmax", "tempmin", "humidity", "conditions", "windspeed", "winddir", "precip"),
                "json");
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInit() {
        return dateInit;
    }

    public void setInit(String init) {
        this.dateInit = init;
    }

    public String getFim() {
        return dateFim;
    }

    public void setFim(String fim) {
        this.dateFim = fim;
    }

    public String getUnitGroup() {
        return unitGroup;
    }

    public void setUnitGroup(String unitGroup) {
        this.unitGroup = unitGroup;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getInclude() {
        return include;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
