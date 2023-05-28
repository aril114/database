package com.example.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTemperatureTest {

    @Test
    void getTemperatures() {
        String address = new String("address");
        double[] temperatures = { 1, 2, 3 };
        var lt = new LocationTemperature(address, temperatures);
        double[] got = lt.getTemperatures();
        Assertions.assertEquals(temperatures.length, got.length);
        for(int i = 0; i<temperatures.length; i++) {
            Assertions.assertEquals(temperatures[i], got[i]);
        }
    }

    @Test
    void getAddress() {
        var address = new String("address");
        double[] temperatures = { 1, 2, 3 };
        var lt = new LocationTemperature(address, temperatures);
        String got = lt.getAddress();
        Assertions.assertEquals(address, got);
    }

    @Test
    void setAddress() {
        var address = new String("address");
        double[] temperatures = { 1, 2, 3 };
        var lt = new LocationTemperature(address, temperatures);
        var newAddress = new String("new address");
        lt.setAddress(newAddress);
        String got = lt.getAddress();
        Assertions.assertEquals(got, newAddress);
    }
}