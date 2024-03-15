package ru.nstu.javafx_labs_lipatov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ModalWindow {
    @FXML
    private Button OKButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea informationTextArea;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    public ModalWindow(){
        fxmlLoader = new FXMLLoader(ModalWindow.class.getResource("modalWindow.fxml"));
        this.stage = new Stage();
    }
    public void newWindow(String title, String data) throws IOException {
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        this.stage.setMaximized(false);
        this.stage.setResizable(false);
        this.stage.setTitle(title);
        this.stage.setScene(scene);
        OKButton = new Button();
        OKButton.setOnAction(event->stage.close());

        this.stage.showAndWait();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
//        Button btn = (Button) event.getSource();
//        switch (btn.getText()) {
//            case "ОК":
//                //this.stage.;
//                System.out.println("OK");
//                break;
//            case "Отмена":
//                this.stage.close();
//                System.out.println("Cancel");
//                break;
//        }
    }

}
