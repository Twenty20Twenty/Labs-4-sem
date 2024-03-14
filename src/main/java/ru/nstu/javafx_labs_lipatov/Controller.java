package ru.nstu.javafx_labs_lipatov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Controller {
    @FXML
    private SplitPane splitPane;
    @FXML
    private Pane visualizationPane;
    @FXML
    private Pane managmentPane;
    @FXML
    private Label statistic;
    @FXML
    private Label labelTextTIMER;
    @FXML
    private Label labelTimer;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonStop;
    @FXML
    private RadioButton informationSwitch;
    @FXML
    private RadioButton radioButtonShowTimer;
    @FXML
    private RadioButton radioButtonHideTimer;
    @FXML
    private ToggleGroup tgTimer;
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
    private void handleButtonAction(ActionEvent event) {
        Button btn = (Button) event.getSource();
        switch (btn.getText()){
            case "Старт":
                if (!Habitat.getInstance().isStartFlag())
                    Habitat.getInstance().startGeneration();
                break;
            case "Стоп":
                if (Habitat.getInstance().isStartFlag())
                    Habitat.getInstance().stopGeneration();
                break;
        }
    }

    @FXML
    private void handleRadioButtonAction(ActionEvent event){
        if (radioButtonShowTimer.isSelected()){
            Habitat.getInstance().showTimer();
        }
        if (radioButtonHideTimer.isSelected()){
            Habitat.getInstance().showTimer();
        }

        if (informationSwitch.isSelected()){
            Habitat.getInstance().setInformationFlag(true);
            Habitat.getInstance().showStatisticLabel();
        }
        else{
            Habitat.getInstance().setInformationFlag(false);
            Habitat.getInstance().setStatisticFlag(false);
            Habitat.getInstance().showStatisticLabel();
        }
    }

    /*
    @FXML
    private void handleButtonStart(ActionEvent event){
        if (!Habitat.getInstance().startFlag)
            Habitat.getInstance().startGeneration();
    }

    @FXML
    private void handleButtonStop(ActionEvent event){
        if (Habitat.getInstance().startFlag)
            Habitat.getInstance().stopGeneration();
    }
    */
    @FXML
    void keyPressed(KeyEvent key){
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
}