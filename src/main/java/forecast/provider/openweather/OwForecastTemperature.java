package forecast.provider.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwForecastTemperature {

    @JsonProperty("temp_min")
    private float min;
    @JsonProperty("temp_max")
    private float max;

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
