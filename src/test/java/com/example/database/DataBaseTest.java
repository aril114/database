package com.example.database;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {

    @Test
    void add_random_lt() {
        var db = new DataBase();
        db.add_random_lt();
        ObservableList<LocationTemperature> listLt = db.getListLt();
        LocationTemperature lt = listLt.get(0);
        double[] temperatures = lt.getTemperatures();
        assertEquals(7, temperatures.length);
        String address = lt.getAddress();
        assertNotNull(address);
        assertTrue(address.length() > 0);
    }

    @Test
    void remove_lt() {
        var db = new DataBase();
        db.add_random_lt();
        ObservableList<LocationTemperature> listLt = db.getListLt();
        assertEquals(1, listLt.size());
        db.remove_lt(0);
        assertEquals(0, listLt.size());
    }
}