package ru.nstu.javafx_labs_lipatov_v2.mvc;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.TreeMap;

public class HabitatView {
    private static int width = 800;
    private static int height = 800;

    public HabitatView() {

    }

    @FXML
    public SplitPane splitPane;
    @FXML
    public MenuItem startMenuItem;
    @FXML
    public MenuItem stopMenuItem;
    @FXML
    public MenuItem showTimeMenuItem;
    @FXML
    public MenuItem hideTimeMenuItem;
    @FXML
    public MenuItem exitMenuItem;
    @FXML
    public MenuItem showInformationMenuItem;
    @FXML
    public MenuItem authorsMenuItem;
    @FXML
    private MenuItem liveObjMenuItem;
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
    private ComboBox priorityMaleComboBox;
    @FXML
    private ComboBox priorityFemaleComboBox;
    @FXML
    private Button applyMaleProp;
    @FXML
    private Button applyFemaleProp;
    @FXML
    private CheckBox informationCheckBox;
    @FXML
    private Button liveObjButton;
    @FXML
    private Button maleAIButton;
    @FXML
    private Button femaleAIButton;
    @FXML
    private Button consoleButton;
    @FXML
    private Button saveObjectsButton;
    @FXML
    private Button loadObjectsButton;


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

    public Button getLiveObjButton() {
        return liveObjButton;
    }

    public Button getApplyMaleProp() {
        return applyMaleProp;
    }

    public Button getApplyFemaleProp() {
        return applyFemaleProp;
    }

    public CheckBox getInformationCheckBox() {
        return informationCheckBox;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public MenuItem getStartMenuItem() {
        return startMenuItem;
    }

    public MenuItem getStopMenuItem() {
        return stopMenuItem;
    }

    public MenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public MenuItem getShowInformationMenuItem() {
        return showInformationMenuItem;
    }

    public MenuItem getShowTimeMenuItem() {
        return showTimeMenuItem;
    }

    public MenuItem getHideTimeMenuItem() {
        return hideTimeMenuItem;
    }

    public MenuItem getAuthorsMenuItem() {
        return authorsMenuItem;
    }

    public MenuItem getLiveObjMenuItem() {
        return liveObjMenuItem;
    }

    public TreeMap<String, Float> getComboBoxMap() {
        return comboBoxMap;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    public Button getMaleAIButton() {
        return maleAIButton;
    }

    public Button getFemaleAIButton() {
        return femaleAIButton;
    }

    public ComboBox getPriorityMaleComboBox() {
        return priorityMaleComboBox;
    }

    public ComboBox getPriorityFemaleComboBox() {
        return priorityFemaleComboBox;
    }
    public Button getConsoleButton() {
        return consoleButton;
    }

    public Button getSaveObjectsButton() {
        return saveObjectsButton;
    }

    public Button getLoadObjectsButton() {
        return loadObjectsButton;
    }
}
