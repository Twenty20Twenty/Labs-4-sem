package ru.nstu.javafx_labs_lipatov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Math.abs;

public class Controller {
    @FXML
    private Pane visualizationPane;
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
    private Button buttonStart;
    @FXML
    private Button buttonStop;
    @FXML
    private TextField maleSpawnTimeTextField;
    @FXML
    private TextField femaleSpawnTimeTextField;
    @FXML
    private ComboBox maleSpawnProbability;
    @FXML
    private ComboBox femaleSpawnProbability;
    @FXML
    private Button applyMaleProp;
    @FXML
    private Button applyFemaleProp;
    @FXML
    private CheckBox informationCheckBox;

    public Pane getVisualPane() {
        return visualizationPane;
    }

    public Label getLabelTextTIMER() {
        return labelTextTIMER;
    }

    public Label getLabelTimer() {
        return labelTimer;
    }


    public RadioButton getRadioButtonShowTimer() {
        return radioButtonShowTimer;
    }

    public RadioButton getRadioButtonHideTimer() {
        return radioButtonHideTimer;
    }

    public TextField getMaleSpawnTimeTextField() {
        return maleSpawnTimeTextField;
    }

    public TextField getFemaleSpawnTimeTextField() {
        return femaleSpawnTimeTextField;
    }

    public ComboBox getMaleSpawnProbabilityBox() {
        return maleSpawnProbability;
    }

    public ComboBox getFemaleSpawnProbabilityBox() {
        return femaleSpawnProbability;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        switch (btn.getText()) {
            case "Старт":
                startFunk();
                break;
            case "Стоп":
                stopFunk();
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

    }

    @FXML
    private void handleInformationsCheckBox(ActionEvent event) {
        if (informationCheckBox.isSelected()) {
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
                startFunk();
                break;
            case E:
                stopFunk();
                break;
            case T:
                Habitat.getInstance().showTimer();
                break;
        }
    }
    private void startFunk(){
        Habitat.getInstance().startGeneration();
        buttonStart.setDisable(true);
        buttonStop.setDisable(false);
        applyMaleProp.setDisable(true);
        maleSpawnTimeTextField.setDisable(true);
        maleSpawnProbability.setDisable(true);
        applyFemaleProp.setDisable(true);
        femaleSpawnTimeTextField.setDisable(true);
        femaleSpawnProbability.setDisable(true);
    }

    private void stopFunk(){
        if (Habitat.getInstance().isStartFlag()) {
            if (Habitat.getInstance().isInformationWindowFlag()) {
                Habitat.getInstance().pauseGeneration();
            } else {
                Habitat.getInstance().stopGeneration();
            }
            if (!Habitat.getInstance().isStartFlag()) {
                buttonStart.setDisable(false);
                buttonStop.setDisable(true);
            }

        }
        applyMaleProp.setDisable(false);
        maleSpawnTimeTextField.setDisable(false);
        maleSpawnProbability.setDisable(false);
        applyFemaleProp.setDisable(false);
        femaleSpawnTimeTextField.setDisable(false);
        femaleSpawnProbability.setDisable(false);
    }
    @FXML
    private void menuStart(ActionEvent event) {
        startFunk();
    }

    @FXML
    private void menuStop(ActionEvent event) {
        stopFunk();
    }

    @FXML
    private void menuShowInformation(ActionEvent event) {
        if (Habitat.getInstance().isInformationWindowFlag()) {
            informationCheckBox.setSelected(false);
            Habitat.getInstance().setInformationWindowFlag(false);
        } else {
            informationCheckBox.setSelected(true);
            Habitat.getInstance().setInformationWindowFlag(true);
        }
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
    void menuExit(ActionEvent event) {
        if (Habitat.getInstance().isStartFlag())
            Habitat.getInstance().getTimer().cancel();
        Habitat.getInstance().clearList();
        Stage stage = (Stage) buttonStart.getScene().getWindow();
        stage.close();
    }

    @FXML
    void maleProperties(ActionEvent event) {
        String userChoiseP = (String) maleSpawnProbability.getSelectionModel().getSelectedItem();
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
                maleSpawnProbability.getSelectionModel().selectLast();
                break;
        }
        String userChoiseN = maleSpawnTimeTextField.getText();
        try {
            int n = Integer.parseInt(userChoiseN);
            if (n < 0){
                n = abs(n);
                Habitat.getInstance().setMaleStudentN(n);
                maleSpawnTimeTextField.setText(String.valueOf(n));
            }
            Habitat.getInstance().setMaleStudentN(n);
        } catch (NumberFormatException e) {
            Habitat.getInstance().setMaleStudentN(2);
            maleSpawnTimeTextField.setText("2");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод периода рождения студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
            alert.showAndWait();
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
        String userChoiseN = femaleSpawnTimeTextField.getText();
        try {
            int n = Integer.parseInt(userChoiseN);
            if (n < 0){
                n = abs(n);
                Habitat.getInstance().setFemaleStudentN(n);
                femaleSpawnTimeTextField.setText(String.valueOf(n));
            }
            Habitat.getInstance().setFemaleStudentN(n);

        } catch (NumberFormatException e) {
            Habitat.getInstance().setFemaleStudentN(3);
            femaleSpawnTimeTextField.setText("3");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод периода рождения студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public Button getButtonStop() {
        return buttonStop;
    }

    public Button getButtonStart() {
        return buttonStart;
    }
}