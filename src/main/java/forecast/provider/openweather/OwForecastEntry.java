package forecast.provider.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OwForecastEntry {

    @JsonProperty("name")
    private String city;
    @JsonProperty("main")
    private OwForecastTemperature temperature;
    @JsonProperty("wind")
    private OwForecastWind wind;
    @JsonProperty("weather")
    private List<OwForecastWeather> weather;

    public String getCity() {
        return city;
    }

    public OwForecastTemperature getTemperature() {
        return temperature;
    }

    public OwForecastWind getWind() {
        return wind;
    }

    public List<OwForecastWeather> getWeather() {
        return weather;
    }
}
