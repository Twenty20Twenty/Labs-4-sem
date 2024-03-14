package ru.nstu.javafx_labs_lipatov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    Habitat school;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        school = new Habitat(fxmlLoader.getController());
        Habitat.setInstance(school);

        Scene scene = new Scene(root, Habitat.getWidth(), Habitat.getHeight());
        scene.getRoot().requestFocus();
        stage.setMaximized(false);
        school.setMaleStudent(5, 0.56F);
        school.setFemaleStudent(2, 0.14F);
        stage.setTitle("Липатов, Драйко");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}