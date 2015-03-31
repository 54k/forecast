package forecast.provider;

public final class City {

    private final String name;
    private final String zipCode;

    public City(String name, String zipCode) {
        this.name = name;
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        City city = (City) o;

        if (!name.equals(city.name)) {
            return false;
        }
        return zipCode.equals(city.zipCode);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + zipCode.hashCode();
        return result;
    }
}
