package forecast.provider.openweather;

import forecast.entity.Forecast;
import forecast.entity.Temperature;
import forecast.entity.Wind;
import forecast.provider.City;
import forecast.provider.ForecastProvider;
import forecast.provider.ForecastProviderException;
import forecast.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class OwForecastProvider extends RestTemplate implements ForecastProvider {

    private static final String PROVIDER_TAG = "OpenWeather";
    private static final String QUERY_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}";

    @Override
    public Collection<Forecast> getCityForecasts(City city) {
        ResponseEntity<OwForecastEntry> response = getForEntity(QUERY_URL,
                OwForecastEntry.class, city.getName());
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ForecastProviderException("Received unsuccessful response from OpenWeather provider");
        }
        return extractForecasts(response.getBody());
    }

    private Collection<Forecast> extractForecasts(OwForecastEntry entry) {
        Forecast forecast = new Forecast();
        forecast.setProvider(PROVIDER_TAG);
        forecast.setCity(entry.getCity());
        forecast.setTime(new Date());
        OwForecastWeather weather = entry.getWeather().get(0);
        forecast.setDescription(Utils.normalizeDescription(weather.getDescription()));
        Temperature temperature = new Temperature();
        float min = entry.getTemperature().getMin();
        temperature.setMin(Utils.kelvinToCelsius(min));
        float max = entry.getTemperature().getMax();
        temperature.setMax(Utils.kelvinToCelsius(max));
        forecast.setTemperature(temperature);
        Wind wind = new Wind();
        wind.setSpeed(entry.getWind().getSpeed());
        wind.setDirection(Utils.degreesToDirection(entry.getWind().getDegrees()));
        forecast.setWind(wind);
        return Arrays.asList(forecast);
    }
}
