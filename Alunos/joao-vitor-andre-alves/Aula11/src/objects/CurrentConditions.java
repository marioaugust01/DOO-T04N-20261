package objects;

public class CurrentConditions {

    private final String datetime;
    private final Double temp;
    private final Double humidity;
    private final String conditions;
    private final Double windspeed;
    private final Double winddir;
    private final Double precip;

    public CurrentConditions(
            String datetime,
            Double temp,
            Double humidity,
            String conditions,
            Double windspeed,
            Double winddir,
            Double precip) {
        this.datetime = datetime;
        this.temp = temp;
        this.humidity = humidity;
        this.conditions = conditions;
        this.windspeed = windspeed;
        this.winddir = winddir;
        this.precip = precip;
    }

    public String getDatetime() {
        return datetime;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public String getConditions() {
        return conditions;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public Double getWinddir() {
        return winddir;
    }

    public Double getPrecip() {
        return precip;
    }
}
