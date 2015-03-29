package forecast.entity;

import com.fasterxml.jackson.annotation.JsonView;
import forecast.api.JsonViews;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Wind {

    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Basic
    private float speed = 0;
    @JsonView({JsonViews.Stored.class, JsonViews.Current.class})
    @Enumerated(value = EnumType.STRING)
    @Basic
    private Direction direction;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public static enum Direction {
        N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW
    }
}
