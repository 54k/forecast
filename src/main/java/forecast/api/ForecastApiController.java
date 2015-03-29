package forecast.api;

import com.fasterxml.jackson.annotation.JsonView;
import forecast.ForecastApplication;
import forecast.entity.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForecastApiController {

    @Autowired
    private ForecastApplication application;

    @JsonView(JsonViews.Stored.class)
    @RequestMapping(value = "/stored", method = RequestMethod.GET)
    public Iterable<Forecast> getStoredForecasts() {
        return application.getStoredForecasts();
    }

    @JsonView(JsonViews.Current.class)
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Iterable<Forecast> getCurrentForecasts() {
        return application.getCurrentForecasts();
    }
}
