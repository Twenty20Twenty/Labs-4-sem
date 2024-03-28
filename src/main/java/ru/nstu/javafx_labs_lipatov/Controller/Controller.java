package ru.nstu.javafx_labs_lipatov.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.nstu.javafx_labs_lipatov.Habitat;
import ru.nstu.javafx_labs_lipatov.objects.FemaleStudent;
import ru.nstu.javafx_labs_lipatov.objects.MaleStudent;
import ru.nstu.javafx_labs_lipatov.objects.StudentCollections;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Controller {
    @FXML
    public MenuItem startMenuItem;
    @FXML
    public MenuItem stopMenuItem;
    @FXML
    public MenuItem showTimeMenuItem;
    @FXML
    public MenuItem hideTimeMenuItem;
    private TreeMap<String, Float> comboBoxMap = new TreeMap<>();
    @FXML
    private Pane visualizationPane;
    @FXML
    private Label labelTextTIMER;
    @FXML
    private Label labelTimer;
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
    private TextField maleLifeTimeTextField;
    @FXML
    private TextField femaleLifeTimeTextField;
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
    @FXML
    private Button liveObjButton;

    public void setComboBoxMap() {
        int procent = 0;
        float tmp = 0.1F;
        for (int i = 0; i < 11; i++) {
            procent = 10 * i;
            tmp = (1.0F - 0.1F * i);
            String tmpStr = Integer.toString(procent) + " %";
            comboBoxMap.put(tmpStr, tmp);
        }
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
    private void liveObjAction(ActionEvent event){}

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

    private void startFunk() {
        Habitat.getInstance().startGeneration();
        buttonStart.setDisable(true);
        buttonStop.setDisable(false);
        applyMaleProp.setDisable(true);
        maleSpawnTimeTextField.setDisable(true);
        maleSpawnProbability.setDisable(true);
        applyFemaleProp.setDisable(true);
        femaleSpawnTimeTextField.setDisable(true);
        femaleSpawnProbability.setDisable(true);
        maleLifeTimeTextField.setDisable(true);
        femaleLifeTimeTextField.setDisable(true);
    }

    private void stopFunk() {
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
        maleLifeTimeTextField.setDisable(false);
        femaleLifeTimeTextField.setDisable(false);
    }

    @FXML
    private void menuStart(ActionEvent event) {
        startFunk();
        startMenuItem.setDisable(true);
        stopMenuItem.setDisable(false);
    }

    @FXML
    private void menuStop(ActionEvent event) {
        stopFunk();
        startMenuItem.setDisable(false);
        stopMenuItem.setDisable(true);
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
        showTimeMenuItem.setDisable(false);
        hideTimeMenuItem.setDisable(true);
    }

    @FXML
    void menuShowTimer(ActionEvent event) {
        Habitat.getInstance().showTimer();
        showTimeMenuItem.setDisable(true);
        hideTimeMenuItem.setDisable(false);
    }

    @FXML
    void menuExit(ActionEvent event) {
        if (Habitat.getInstance().isStartFlag())
            Habitat.getInstance().getTimer().cancel();
        StudentCollections.getInstance().clearCollections(this);
        Stage stage = (Stage) buttonStart.getScene().getWindow();
        stage.close();
    }

    @FXML
    void maleProperties(ActionEvent event) {
        String userChoiseP = (String) maleSpawnProbability.getSelectionModel().getSelectedItem();
        if (userChoiseP == null)
            userChoiseP = "null";

        for (Map.Entry<String, Float> entry : comboBoxMap.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();
            if (userChoiseP.equals(key)) {
                Habitat.getInstance().setMaleStudentP(value);
            }
        }

        String userChoiseN = maleSpawnTimeTextField.getText();
        try {
            int n = Integer.parseInt(userChoiseN);
            if (n < 0) {
                throw new NumberFormatException();
            }
            Habitat.getInstance().setMaleStudentN(n);
        } catch (NumberFormatException e) {
            Habitat.getInstance().setMaleStudentN(2);
            maleSpawnTimeTextField.setText("2");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ПЕРИОДА РОЖДЕНИЯ студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
            alert.showAndWait();
        }

        String userChoiseLifeT = maleLifeTimeTextField.getText();
        try {
            int n = Integer.parseInt(userChoiseLifeT);
            if (n < 0) {
                throw new NumberFormatException();
            }
            MaleStudent.setLiveTime(n);
        } catch (NumberFormatException e) {
            MaleStudent.setLiveTime(4);
            maleLifeTimeTextField.setText("4");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ВРЕМЕНИ ЖИЗНИ студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void femaleProperties(ActionEvent event) {
        String userChoiseP = (String) femaleSpawnProbability.getSelectionModel().getSelectedItem();
        if (userChoiseP == null)
            userChoiseP = "null";

        for (Map.Entry<String, Float> entry : comboBoxMap.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();
            if (userChoiseP.equals(key)) {
                Habitat.getInstance().setFemaleStudentP(value);
            }
        }

        String userChoiseN = femaleSpawnTimeTextField.getText();
        try {
            int n = Integer.parseInt(userChoiseN);
            if (n < 0) {
                throw new NumberFormatException();
            }
            Habitat.getInstance().setFemaleStudentN(n);
        } catch (NumberFormatException e) {
            Habitat.getInstance().setFemaleStudentN(3);
            femaleSpawnTimeTextField.setText("3");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ПЕРИОДА РОЖДЕНИЯ студентки. Разрешено вводить только целые положительные числа", ButtonType.OK);
            alert.showAndWait();
        }

        String userChoiseLifeT = femaleLifeTimeTextField.getText();
        try {
            int n = Integer.parseInt(userChoiseLifeT);
            if (n < 0) {
                throw new NumberFormatException();
            }
            FemaleStudent.setLiveTime(n);
        } catch (NumberFormatException e) {
            FemaleStudent.setLiveTime(5);
            femaleLifeTimeTextField.setText("5");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ВРЕМЕНИ ЖИЗНИ студентки. Разрешено вводить только целые положительные числа", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public Button getButtonStop() {
        return buttonStop;
    }

    public Button getButtonStart() {
        return buttonStart;
    }

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

    public TextField getMaleLifeTimeTextField() {
        return maleLifeTimeTextField;
    }

    public TextField getFemaleLifeTimeTextField() {
        return femaleLifeTimeTextField;
    }
}