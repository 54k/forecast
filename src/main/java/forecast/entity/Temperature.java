package forecast.entity;

import com.fasterxml.jackson.annotation.JsonView;
import forecast.api.JsonViews;

import javax.persistence.Embeddable;

@Embeddable
public class Temperature {
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    private float min = 0;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    private float max = 0;

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
