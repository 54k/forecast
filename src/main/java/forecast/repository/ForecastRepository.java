package forecast.repository;

import forecast.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

interface ForecastRepository extends JpaRepository<Forecast, Long> {
}
