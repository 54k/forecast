package forecast.provider.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OwForecastWind {

    @JsonProperty("speed")
    private float speed;
    @JsonProperty("deg")
    private float degrees;

    public float getSpeed() {
        return speed;
    }

    public float getDegrees() {
        return degrees;
    }
}
