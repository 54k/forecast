package forecast;

import forecast.provider.City;
import forecast.provider.cdyn.CdynForecastProvider;
import forecast.provider.openweather.OwForecastProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "forecast")
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableScheduling
@EnableWebMvc
public class ForecastConfiguration extends WebMvcConfigurerAdapter {

    private static final String CDYN_FORECAST_URL = "http://wsf.cdyne.com/WeatherWS/Weather.asmx";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("forecast.wsdl");
        return marshaller;
    }

    @Bean
    public CdynForecastProvider cdynForecastProvider(Jaxb2Marshaller marshaller) {
        CdynForecastProvider provider = new CdynForecastProvider();
        provider.setDefaultUri(CDYN_FORECAST_URL);
        provider.setMarshaller(marshaller);
        provider.setUnmarshaller(marshaller);
        return provider;
    }

    @Bean
    public OwForecastProvider owForecastProvider() {
        return new OwForecastProvider();
    }

    @Bean(name = "cities")
    public List<City> cities() {
        return Arrays.asList(
                new City("Miami", "33111"),
                new City("New York", "10007"),
                new City("Los Angeles", "90001")
        );
    }

    @Bean(name = "schedulePeriod")
    public long schedulePeriod() {
        return TimeUnit.HOURS.toMillis(1);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(0);
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(0);
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index.html");
    }
}
