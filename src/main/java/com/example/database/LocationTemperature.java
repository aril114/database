// todo: author
package com.example.database;

import java.io.Serializable;

/** Класс содержит данные о температуре на следующие несколько дней
 * */
public class LocationTemperature implements Serializable {
    /** адрес*/
    private String address;
    /** массив температур по дням, начиная с сегодняшнего */
    private double temperatures[];

    /** конструктор LocationTemperature. принимает адресс и массив температур. */
    public LocationTemperature(String anAddress, double temperatures[]) {
        address = anAddress;
        if (temperatures.length > 0) {
            this.temperatures = temperatures;
        }
        else throw new IllegalArgumentException("Передан пустой массив температур");
    }

    public Double get0th() { return temperatures[0]; }
    public Double get1th() { return temperatures[1]; }
    public Double get2th() { return temperatures[2]; }
    public Double get3th() { return temperatures[3]; }
    public Double get4th() { return temperatures[4]; }
    public Double get5th() { return temperatures[5]; }
    public Double get6th() { return temperatures[6]; }

    public double[] getTemperatures() {
        return temperatures;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTemperature(double t, int index) {
        if (index >= temperatures.length) {
            throw new IllegalArgumentException("индекс изменяемого элемента превышает максимальный");
        }
        temperatures[index] = t;
    }

    public String toString() {
        var builder = new StringBuilder();
        builder.append(address + ": ");
        int len = temperatures.length;
        for (int i=0; i<len-1; i++) {
            builder.append(temperatures[i] + " ");
        }
        builder.append(temperatures[len-1]);
        return builder.toString();
    }
}
