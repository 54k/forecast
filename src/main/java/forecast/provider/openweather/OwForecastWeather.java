package forecast.provider.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwForecastWeather {

    @JsonProperty("description")
    private String description;

    public String getDescription() {
        return description;
    }
}
