package ru.nstu.javafx_labs_lipatov;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ButtonController {
    @FXML
    private GridPane pane;
    @FXML
    private Pane modalPane;
    @FXML
    private Label statistic;
    @FXML
    private Label labelTextTIMER;
    @FXML
    private Label labelTimer;

    public Pane getPane() {
        return modalPane;
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
    @FXML
    void keyPressed(KeyEvent key){
        key.consume();
        switch (key.getCode()) {
            case B:
                if (!Habitat.getInstance().startFlag)
                    Habitat.getInstance().startGeneration();
                break;
            case E:
                if (Habitat.getInstance().startFlag)
                    Habitat.getInstance().stopGeneration();
                break;
            case T:
                Habitat.getInstance().showTimer();
                break;
        }
    }
}