// todo: author
package com.example.database;


// todo: doc
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Отвечает за хранение, загрузку, сохранение данных
 **/
public class DataBase {
    // ObservableList - класс, с объектами которого работает таблица из JavaFX
    private ObservableList<LocationTemperature> listLt = FXCollections.observableArrayList();
    // FXCollections.observableArrayList() -- метод, создающий экземпляр класса ObservableArrayList

    /**  добавляет в список прогнозов температур list_lt случайную строку */
    public void add_random_lt(){
        listLt.sorted();
        Random rand = new Random();
        var temperatures = new double[7];
        for (int i = 0; i < 7; i++) {
            temperatures[i] = rand.nextDouble()*80-40; // заполняет массив случайными значениями от -40 до +40
        }
        LocationTemperature lt = new LocationTemperature("Город №" + ( (rand.nextInt() % 50) + 50) % 50, // получаем число от 0 до 49
                temperatures);
        listLt.add( lt );
    }

    /** удаляет из list_lt элемент с заданным индексом */
    public void remove_lt(int index) {
        if (index >= listLt.size() || index < 0 ) {
            throw new IllegalArgumentException("Попытка удаления несуществующего элемента из бд.");
        }
        listLt.remove(index);
    }

    /** загружает из файла базу данных */
    public void load(String fileName) throws IOException, IllegalArgumentException  {
        try (Scanner inFile = new Scanner(Path.of(fileName), StandardCharsets.UTF_8)) {
            listLt.clear();
            while (inFile.hasNext()) {
                String line = inFile.nextLine();
                Pattern pattern = Pattern.compile("(?iuU)(.+): (-?\\d+.\\d+) (-?\\d+.\\d+) (-?\\d+.\\d+) (-?\\d+.\\d+) (-?\\d+.\\d+) (-?\\d+.\\d+) (-?\\d+.\\d+)");
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    throw (new IllegalArgumentException("файл " + fileName + " содержит некорректные данные."));
                }
                var temperatures = new double[7];
                for (int i=2; i<=8; i++) {
                    temperatures[i-2] = Double.valueOf(matcher.group(i));
                }
                listLt.add(new LocationTemperature(matcher.group(1), temperatures));
            }
        }
    }

    /** записывает базу данных в файл  */
    public void save(String fileName) throws IOException {
        // строки в Джаве неизменяемы, поэтому, если к параметру дописать final, на деле ничего не изменится
        try (var out = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
            for (LocationTemperature lt: listLt) {
                out.print(lt.toString());
                out.println();
            }
        }
    }

    /** ищет указанный адрес в listLt и возвращает индекс искомого элемента, если находит  */
    public int searchAddress(String pattern) {
        for (int i=0; i<listLt.size(); i++) {
            if (listLt.get(i).getAddress().contains(pattern)) {
                return i;
            }
        }
        return -1;
    }

    /** возвращает list_lt */
    public ObservableList<LocationTemperature> getListLt() {
        return listLt;
    }
}