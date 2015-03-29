package forecast.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import forecast.api.JsonViews;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(value = {"new", "id"}, ignoreUnknown = true)
@Entity
public class Forecast extends AbstractPersistable<Long> {

    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Basic
    private String city;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Basic
    private String description;
    @JsonView(JsonViews.Stored.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date storeTime;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Basic
    private Date time;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Embedded
    private Temperature temperature;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Embedded
    private Wind wind;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Basic
    private String provider;

    @PrePersist
    public void onStore() {
        storeTime = new Date();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
