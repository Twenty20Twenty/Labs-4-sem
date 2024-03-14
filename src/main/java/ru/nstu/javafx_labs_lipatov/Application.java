package ru.nstu.javafx_labs_lipatov;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    Habitat school;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        school = new Habitat(fxmlLoader.getController());
        Habitat.setInstance(school);

        Scene scene = new Scene(root, Habitat.getWidth(), Habitat.getHeight());
        scene.getRoot().requestFocus();
        stage.setMaximized(false);
        stage.setResizable(false);
        school.setMaleStudent(1, 0.1F);
        school.setFemaleStudent(1, 0.14F);
        stage.setTitle("Студенты и Студентки");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}