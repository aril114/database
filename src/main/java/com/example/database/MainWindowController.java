// todo: doc
package com.example.database;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

// todo: doc
public class MainWindowController implements Initializable {
    /** представление данных: будет показывать данные из ObservableList */
    @FXML
    private TableView<LocationTemperature> table;
    @FXML
    private Label label;

    @FXML
    private TextField patternField;

    // Будут отображать отдельные поля класса Student в ячейки соответствующих строк
    // <Student, String> - из какого класса брать данные, в каком типе представлять в TableView
    // см. связывание метода из Student и колонки, добавление колонки в таблицу ниже
    private TableColumn<LocationTemperature, String> col_address = new TableColumn<>("Address");
    private static String[] colNames = new String[] { "сегодня", "завтра", "2 дня", "3 дня", "4 дня", "5 дней", "6 дней" } ;

    /** содержит модель данных (ObservableList) */
    private DataBase db = new DataBase();

    /** Вызывается после того как форма создана. Можно обращаться к её содержимому */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // добавление колонок в таблицу
        table.getColumns().add(col_address);

        for (int i = 0; i < 7; i++) {
            TableColumn<LocationTemperature, Double> colTemp = new TableColumn<>(colNames[i]);
            colTemp.setCellValueFactory(new PropertyValueFactory<>(String.valueOf(i) + "th"));
            table.getColumns().add(colTemp);
        }

        // связывает колонку и метод из Student, с помощью которого колонка будет получать значения для каждой ячейки данных
        // аргумента PropertyValueFactory должен быь таким, чтобы получить име геттера и сеттера добавив get и set соответственно
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));

        // связывание таблицы и модели данных
        table.setItems( db.getListLt() );
    }

    /** Добавление строки со случайным содержимым в таблицу */
    @FXML
    protected void onAddRandomRowButtonClick() {
        db.add_random_lt();
    }

    @FXML
    protected void onRemoveRowButtonClick() {
        int i = table.getSelectionModel().getSelectedIndex();
        if (i == -1) {
            showMessage("выберите строку", 2);
        }
        else {
            db.remove_lt(i);
        }
    }

    @FXML
    protected void onSaveButtonClick() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(Paths.get(".").toFile());
            fileChooser.setInitialFileName("db.txt");
            File selectedFile = fileChooser.showSaveDialog(label.getScene().getWindow());
            if (selectedFile != null) {
                db.save(selectedFile.toString());
            }
        }
        catch (IOException ex) {
            showMessage(ex.getMessage(), 5);
        }
    }

    @FXML
    protected void onLoadButtonClick() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(Paths.get(".").toFile());
            File selectedFile = fileChooser.showOpenDialog(label.getScene().getWindow());
            if (selectedFile != null) {
                db.load(selectedFile.toString());
            }
        }
        catch (Exception ex) {
            showMessage(ex.getMessage(), 5);
        }
    }

    @FXML
    protected void onFindButtonClick() {
        int index = db.searchAddress(patternField.getText());
        if (index == -1) {
            showMessage("строки не найдено", 5);        // 5 seconds
        }
        else table.getSelectionModel().select(index);
    }

    @FXML
    protected void onAboutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText(null);
        alert.setContentText("База данных с графическим интерфейсом.");

        alert.showAndWait();
    }

    /** отображает сообщение заданное время на фоне окна */
    private void showMessage(String message, int duration) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(e -> label.setText(null));
        pause.play();
    }


}