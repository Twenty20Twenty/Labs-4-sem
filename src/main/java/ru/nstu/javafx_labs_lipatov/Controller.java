package ru.nstu.javafx_labs_lipatov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {
    @FXML
    private Pane visualizationPane;
    @FXML
    private Label statistic;
    @FXML
    private Label labelTextTIMER;
    @FXML
    private Label labelTimer;
    @FXML
    private RadioButton informationSwitch;
    @FXML
    private RadioButton radioButtonShowTimer;
    @FXML
    private RadioButton radioButtonHideTimer;
    @FXML
    private TextField maleSpawnTime;
    @FXML
    private TextField femaleSpawnTime;
    @FXML
    private ComboBox maleSpawnProbability;
    @FXML
    private ComboBox femaleSpawnProbability;

    public Pane getVisualPane() {
        return visualizationPane;
    }

    public Label getLabelTextTIMER() {
        return labelTextTIMER;
    }

    public Label getLabelTimer() {
        return labelTimer;
    }

    public Label getStatisticLabel() {
        return statistic;
    }

    public RadioButton getRadioButtonShowTimer() {
        return radioButtonShowTimer;
    }

    public RadioButton getRadioButtonHideTimer() {
        return radioButtonHideTimer;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        switch (btn.getText()) {
            case "Старт":
                if (!Habitat.getInstance().isStartFlag())
                    Habitat.getInstance().startGeneration();
                break;
            case "Стоп":
                if (Habitat.getInstance().isStartFlag()) {
                    if (!Habitat.getInstance().isInformationWindowFlag()) {
                        Habitat.getInstance().stopGeneration();
                    } else {
                        ModalWindow ttt = new ModalWindow();
                        ttt.newWindow("Information Window", "Студентов: 3\nСтуденток: 7");

                    }
                }
                break;
        }
    }

    @FXML
    private void handleRadioButtonAction(ActionEvent event) {
        if (radioButtonShowTimer.isSelected()) {
            Habitat.getInstance().showTimer();
        }
        if (radioButtonHideTimer.isSelected()) {
            Habitat.getInstance().showTimer();
        }

        if (informationSwitch.isSelected()) {
            Habitat.getInstance().setInformationWindowFlag(true);
        } else {
            Habitat.getInstance().setInformationWindowFlag(false);
        }
    }

    @FXML
    void keyPressed(KeyEvent key) {
        key.consume();
        switch (key.getCode()) {
            case B:
                if (!Habitat.getInstance().isStartFlag())
                    Habitat.getInstance().startGeneration();
                break;
            case E:
                if (Habitat.getInstance().isStartFlag())
                    Habitat.getInstance().stopGeneration();
                break;
            case T:
                Habitat.getInstance().showTimer();
                break;
        }
    }

    @FXML
    void menuStart(ActionEvent event) {
        if (!Habitat.getInstance().isStartFlag())
            Habitat.getInstance().startGeneration();
    }

    @FXML
    void menuStop(ActionEvent event) {
        if (Habitat.getInstance().isStartFlag())
            Habitat.getInstance().stopGeneration();
    }

    @FXML
    void menuHideTimer(ActionEvent event) {
        Habitat.getInstance().showTimer();
    }

    @FXML
    void menuShowTimer(ActionEvent event) {
        Habitat.getInstance().showTimer();
    }

    @FXML
    void maleProperties(ActionEvent event) {
        String userChoiseP = (String) maleSpawnProbability.getSelectionModel().getSelectedItem();
        System.out.println(userChoiseP);
        if (userChoiseP == null)
            userChoiseP = "null";
        switch (userChoiseP) {
            case "0 %":
                Habitat.getInstance().setMaleStudentP(1.0f);
                break;
            case "10 %":
                Habitat.getInstance().setMaleStudentP(0.9f);
                break;
            case "20 %":
                Habitat.getInstance().setMaleStudentP(0.8f);
                break;
            case "30 %":
                Habitat.getInstance().setMaleStudentP(0.7f);
                break;
            case "40 %":
                Habitat.getInstance().setMaleStudentP(0.6f);
                break;
            case "50 %":
                Habitat.getInstance().setMaleStudentP(0.5f);
                break;
            case "60 %":
                Habitat.getInstance().setMaleStudentP(0.4f);
                break;
            case "70 %":
                Habitat.getInstance().setMaleStudentP(0.3f);
                break;
            case "80 %":
                Habitat.getInstance().setMaleStudentP(0.2f);
                break;
            case "90 %":
                Habitat.getInstance().setMaleStudentP(0.1f);
                break;
            case "100 %":
                Habitat.getInstance().setMaleStudentP(0f);
                break;
            case "null":
                Habitat.getInstance().setMaleStudentP(0f);
                break;
        }
        String userChoiseN = maleSpawnTime.getText();
        try {
            Habitat.getInstance().setMaleStudentN(Integer.parseInt(userChoiseN));
        }
        catch (NumberFormatException e){
            Habitat.getInstance().setMaleStudentN(5);
            maleSpawnTime.setText("5");
            e.printStackTrace();
        }
    }

    @FXML
    void femaleProperties(ActionEvent event) {
        String userChoiseP = (String) femaleSpawnProbability.getSelectionModel().getSelectedItem();
        if (userChoiseP == null)
            userChoiseP = "null";
        switch (userChoiseP) {
            case "0 %":
                Habitat.getInstance().setFemaleStudentP(1.0f);
                break;
            case "10 %":
                Habitat.getInstance().setFemaleStudentP(0.9f);
                break;
            case "20 %":
                Habitat.getInstance().setFemaleStudentP(0.8f);
                break;
            case "30 %":
                Habitat.getInstance().setFemaleStudentP(0.7f);
                break;
            case "40 %":
                Habitat.getInstance().setFemaleStudentP(0.6f);
                break;
            case "50 %":
                Habitat.getInstance().setFemaleStudentP(0.5f);
                break;
            case "60 %":
                Habitat.getInstance().setFemaleStudentP(0.4f);
                break;
            case "70 %":
                Habitat.getInstance().setFemaleStudentP(0.3f);
                break;
            case "80 %":
                Habitat.getInstance().setFemaleStudentP(0.2f);
                break;
            case "90 %":
                Habitat.getInstance().setFemaleStudentP(0.1f);
                break;
            case "100 %":
                Habitat.getInstance().setFemaleStudentP(0f);
                break;
            case "null":
                Habitat.getInstance().setFemaleStudentP(0f);
                femaleSpawnProbability.getSelectionModel().selectLast();
                break;
        }
        String userChoiseN = femaleSpawnTime.getText();
        try {
            Habitat.getInstance().setFemaleStudentN(Integer.parseInt(userChoiseN));
        } catch (NumberFormatException e) {
            Habitat.getInstance().setFemaleStudentN(5);
            femaleSpawnTime.setText("5");
            e.printStackTrace();
        }
    }

}