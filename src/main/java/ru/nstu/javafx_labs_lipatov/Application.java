package ru.nstu.javafx_labs_lipatov;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Application extends javafx.application.Application {
    Habitat school;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("controller.fxml"));
        Parent root = fxmlLoader.load();
        school = new Habitat(fxmlLoader.getController());
        Habitat.setInstance(school);

        Scene scene = new Scene(root, Habitat.getWidth(), Habitat.getHeight());
        scene.getRoot().requestFocus();
        stage.setMaximized(false);
        stage.setResizable(false);
        school.setMaleStudentP(0.5F);
        school.setMaleStudentN(2);
        school.setFemaleStudentP(0.2F);
        school.setFemaleStudentN(3);
        school.getController().getMaleSpawnTimeTextField().setText("2");
        school.getController().getMaleSpawnProbabilityBox().getSelectionModel().select("50 %");
        school.getController().getFemaleSpawnProbabilityBox().getSelectionModel().select("80 %");
        school.getController().getFemaleSpawnTimeTextField().setText("3");
        stage.setTitle("Студенты и Студентки");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (school.isStartFlag())
                    school.getTimer().cancel();
                school.clearList();
                stage.close();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}