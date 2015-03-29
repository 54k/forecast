package forecast.provider.cdyn;

import forecast.entity.Forecast;
import forecast.entity.Temperature;
import forecast.entity.Wind;
import forecast.provider.City;
import forecast.provider.ForecastProvider;
import forecast.provider.ForecastProviderException;
import forecast.util.Utils;
import forecast.wsdl.GetCityWeatherByZIP;
import forecast.wsdl.GetCityWeatherByZIPResponse;
import forecast.wsdl.WeatherReturn;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class CdynForecastProvider extends WebServiceGatewaySupport implements ForecastProvider {

    private static final String PROVIDER_TAG = "CDYN";
    private static final String SOAP_ACTION_URL = "http://ws.cdyne.com/WeatherWS/GetCityWeatherByZIP";

    @Override
    public Collection<Forecast> getCityForecasts(City city) {
        GetCityWeatherByZIP request = new GetCityWeatherByZIP();
        request.setZIP(city.getZipCode());
        GetCityWeatherByZIPResponse response = (GetCityWeatherByZIPResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(SOAP_ACTION_URL));
        WeatherReturn result = response.getGetCityWeatherByZIPResult();
        if (!result.isSuccess()) {
            throw new ForecastProviderException("Received unsuccessful response from CDYN provider");
        }
        return extractResult(result);
    }

    private Collection<Forecast> extractResult(WeatherReturn result) {
        Forecast forecast = new Forecast();
        forecast.setProvider(PROVIDER_TAG);
        forecast.setCity(result.getCity());
        forecast.setTime(new Date());
        Temperature temperature = new Temperature();
        float minAndMax = Utils.parseFloat(result.getTemperature());
        temperature.setMin(Utils.fahrenheitToCelsius(minAndMax));
        temperature.setMax(Utils.fahrenheitToCelsius(minAndMax));
        forecast.setTemperature(temperature);
        Wind wind = Utils.parseWindFromCdynString(result.getWind());
        forecast.setWind(wind);
        forecast.setDescription(Utils.normalizeDescription(result.getDescription()));
        return Arrays.asList(forecast);
    }
}
