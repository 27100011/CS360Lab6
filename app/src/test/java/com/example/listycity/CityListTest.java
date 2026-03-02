package com.example.listycity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the CityList class following Lab 6 requirements.
 */
class CityListTest {

    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    /**
     * Tests hasCity(City city) - returns whether or not it belongs in the list.
     */
    @Test
    void testHasCity() {
        CityList cityList = mockCityList();
        City city = mockCity();
        assertTrue(cityList.hasCity(city)); // Checks if Edmonton is present

        City newCity = new City("Saskatoon", "Saskatchewan");
        assertFalse(cityList.hasCity(newCity)); // Checks a city not in the list
    }

    /**
     * Tests delete(City city) - removes city or throws exception if not found.
     */
    @Test
    void testDelete() {
        CityList cityList = mockCityList();
        City city = mockCity();

        // Verify city is removed correctly
        cityList.delete(city);
        assertFalse(cityList.hasCity(city));
        assertEquals(0, cityList.countCities());
    }

    /**
     * Tests if delete(City city) actually throws an exception when city is missing.
     */
    @Test
    void testDeleteException() {
        CityList cityList = mockCityList();
        City city = new City("Vancouver", "BC");

        // Verify exception is thrown for a missing city
        assertThrows(IllegalArgumentException.class, () -> {
            cityList.delete(city);
        });
    }

    /**
     * Tests countCities() - returns how many cities are in the list.
     */
    @Test
    void testCountCities() {
        CityList cityList = mockCityList();
        // mockCityList starts with 1 city
        assertEquals(1, cityList.countCities());

        cityList.add(new City("Toronto", "Ontario"));
        assertEquals(2, cityList.countCities()); // Verify count increases
    }
}