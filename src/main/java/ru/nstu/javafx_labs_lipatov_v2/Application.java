package ru.nstu.javafx_labs_lipatov_v2;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatController;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatModel;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatView;
import ru.nstu.javafx_labs_lipatov_v2.AI.*;

import java.io.IOException;

public class Application extends javafx.application.Application {
    HabitatView view;
    HabitatModel model;
    HabitatController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        System.out.println();
        view = new HabitatView();
        view = fxmlLoader.getController();
        view.setComboBoxMap();
        model = new HabitatModel(view);
        controller = new HabitatController(view, model);

        Scene scene = new Scene(root, view.getWidth(), view.getHeight());
        scene.getRoot().requestFocus();
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setTitle("Студенты и Студентки");
        stage.setScene(scene);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (model.isStartFlag()){
                    model.getTimer().cancel();
                }
                StudentCollections.getInstance().clearCollections(view);
                Stage stage = (Stage) view.getButtonStart().getScene().getWindow();
                stage.close();

                if (model.maleAI.paused)
                    model.maleAI.interrupt();
                if (model.femaleAI.paused)
                    model.femaleAI.interrupt();

                model.maleAI.end();
                model.femaleAI.end();

                controller.saveProperties();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}