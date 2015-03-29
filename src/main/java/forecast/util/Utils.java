package forecast.util;

import forecast.entity.Wind;

public final class Utils {

    private static final float DIRECTION_STEP = 22.5f;
    private static final float FEET_DIVIDER = 3.2808f;

    private Utils() {
    }

    public static String normalizeDescription(String string) {
        if ("N/A".equalsIgnoreCase(string)) {
            return string.toUpperCase();
        }
        return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
    }

    public static float parseFloat(String temperature) {
        try {
            return Float.parseFloat(temperature);
        } catch (NumberFormatException ignore) {
            return 0;
        }
    }

    public static float fahrenheitToCelsius(float fahrenheit) {
        return (fahrenheit - 32) / 1.8f;
    }

    public static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.15f;
    }

    public static Wind parseWindFromCdynString(String string) {
        Wind wind = new Wind();
        if ("CALM".equals(string)) {
            wind.setSpeed(0);
            wind.setDirection(Wind.Direction.N);
        } else {
            int splitIndex = 0;
            for (int i = 0; i < string.length(); i++) {
                if (Character.isDigit(string.charAt(i))) {
                    splitIndex = i;
                    break;
                }
            }
            String direction = string.substring(0, splitIndex);
            float speed = parseFloat(string.substring(splitIndex));
            wind.setDirection(Wind.Direction.valueOf(direction.toUpperCase()));
            wind.setSpeed(feetToMeters(speed));
        }
        return wind;
    }

    private static float feetToMeters(float feet) {
        return feet / FEET_DIVIDER;
    }

    public static Wind.Direction degreesToDirection(float degrees) {
        int directionIndex = (int) (degrees / DIRECTION_STEP) % Wind.Direction.values().length;
        return Wind.Direction.values()[directionIndex];
    }
}
