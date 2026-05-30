package objects;

public class WeatherDay {

    private final String date;
    private final Double tempmax;
    private final Double tempmin;

    public WeatherDay(String date, Double tempmax, Double tempmin) {
        this.date = date;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
    }

    public String getDate() {
        return date;
    }

    public Double getTempmax() {
        return tempmax;
    }

    public Double getTempmin() {
        return tempmin;
    }
}
