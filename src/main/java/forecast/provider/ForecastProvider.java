package forecast.provider;

import forecast.entity.Forecast;

import java.util.Collection;

public interface ForecastProvider {

    /**
     * @throws forecast.provider.ForecastProviderException
     */
    Collection<Forecast> getCityForecasts(City city);
}
