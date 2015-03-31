package forecast;

import forecast.entity.Forecast;
import forecast.provider.City;
import forecast.provider.ForecastProvider;
import forecast.repository.ForecastRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ForecastApplication.class);

    @Autowired
    private ForecastRepositoryService repository;
    @Autowired
    private List<ForecastProvider> providers;
    @Resource(name = "cities")
    private List<City> cities;
    @Resource(name = "schedulePeriod")
    private long schedulePeriod;
    @Autowired
    private TaskScheduler scheduler;

    @PostConstruct
    public void postConstruct() {
        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                storeForecasts();
            }
        }, schedulePeriod);
    }

    private void storeForecasts() {
        Iterable<Forecast> forecasts = getCurrentForecasts();
        repository.storeForecasts(forecasts);
    }

    public Iterable<Forecast> getCurrentForecasts() {
        List<Forecast> forecasts = new ArrayList<>();
        for (ForecastProvider provider : providers) {
            for (City city : cities) {
                try {
                    forecasts.addAll(provider.getCityForecasts(city));
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return forecasts;
    }

    public Iterable<Forecast> getStoredForecasts() {
        return repository.getForecasts();
    }
}
