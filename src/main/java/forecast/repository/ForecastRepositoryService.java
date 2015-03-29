package forecast.repository;

import forecast.entity.Forecast;

import java.util.List;

public interface ForecastRepositoryService {

    List<Forecast> getForecasts();

    List<Forecast> storeForecasts(Iterable<Forecast> forecasts);
}
