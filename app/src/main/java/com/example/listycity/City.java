package com.example.listycity;

import java.util.Objects;

/**
 * This is a class that defines a City.
 */
public class City implements Comparable<City> {
    private String city;
    private String province;

    /**
     * Constructs a City object.
     * @param city The name of the city.
     * @param province The name of the province.
     */
    City(String city, String province) {
        this.city = city;
        this.province = province;
    }

    /**
     * Gets the name of the city.
     * @return The city name as a String.
     */
    String getCityName() {
        return this.city;
    }

    /**
     * Gets the name of the province.
     * @return The province name as a String.
     */
    String getProvinceName() {
        return this.province;
    }

    /**
     * Compares the current city name with another city name for sorting.
     * @param city The other city object to compare against.
     * @return An integer (0 if names are equal).
     */
    @Override
    public int compareTo(City city) {
        return this.city.compareTo(city.getCityName());
    }

    /**
     * Overridden equals method to compare City objects based on content.
     * Required for hasCity and delete logic.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city1 = (City) o;
        return Objects.equals(city, city1.city) && Objects.equals(province, city1.province);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, province);
    }


}