package forecast.repository;

import forecast.entity.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultForecastRepositoryService implements ForecastRepositoryService {

    @Autowired
    private ForecastRepository repository;

    @Override
    public List<Forecast> getForecasts() {
        return repository.findAll();
    }

    @Override
    public List<Forecast> storeForecasts(Iterable<Forecast> forecasts) {
        return repository.save(forecasts);
    }
}
