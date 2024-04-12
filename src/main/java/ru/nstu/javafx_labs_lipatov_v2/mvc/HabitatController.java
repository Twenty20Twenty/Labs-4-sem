package ru.nstu.javafx_labs_lipatov_v2.mvc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.MaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

import java.util.Map;

public class HabitatController {
    private HabitatView view;
    private HabitatModel model;

    public HabitatController(HabitatView view, HabitatModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.getSplitPane().setOnKeyPressed(keyEvent -> {
            keyEvent.consume();
            switch (keyEvent.getCode()) {
                case B:
                    startFunk();
                    break;
                case E:
                    stopFunk();
                    break;
                case T:
                    model.showTimer();
                    break;
            }
        });
        view.getButtonStart().setOnAction(event -> startFunk());
        view.getButtonStop().setOnAction(event -> stopFunk());
        view.getRadioButtonHideTimer().setOnAction(event -> model.showTimer());
        view.getRadioButtonShowTimer().setOnAction(event -> model.showTimer());
        view.getStartMenuItem().setOnAction(event -> startFunk());
        view.getStopMenuItem().setOnAction(event -> stopFunk());
        view.getExitMenuItem().setOnAction(event -> {
            if (model.isStartFlag())
                model.getTimer().cancel();
            StudentCollections.getInstance().clearCollections(view);
            Stage stage = (Stage) view.getButtonStart().getScene().getWindow();
            stage.close();
            model.maleAI.end();
            model.femaleAI.end();
        });
        view.getShowTimeMenuItem().setOnAction(event -> model.showTimer());
        view.getHideTimeMenuItem().setOnAction(event -> model.showTimer());
        view.getShowInformationMenuItem().setOnAction(event -> {
            if (model.isInformationWindowFlag()) {
                view.getInformationCheckBox().setSelected(false);
                model.setInformationWindowFlag(false);
            } else {
                view.getInformationCheckBox().setSelected(true);
                model.setInformationWindowFlag(true);
            }
        });
        view.getApplyFemaleProp().setOnAction(event -> {
            String userChoiseP = (String) view.getFemaleSpawnProbabilityBox().getSelectionModel().getSelectedItem();
            if (userChoiseP == null)
                userChoiseP = "null";

            for (Map.Entry<String, Float> entry : view.getComboBoxMap().entrySet()) {
                String key = entry.getKey();
                Float value = entry.getValue();
                if (userChoiseP.equals(key)) {
                    model.setFemaleStudentP(value);
                }
            }

            String userChoiseN = view.getFemaleSpawnTimeTextField().getText();
            try {
                int n = Integer.parseInt(userChoiseN);
                if (n < 0) {
                    throw new NumberFormatException();
                }
                model.setFemaleStudentN(n);
            } catch (NumberFormatException e) {
                model.setFemaleStudentN(3);
                view.getFemaleSpawnTimeTextField().setText("3");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ПЕРИОДА РОЖДЕНИЯ студентки. Разрешено вводить только целые положительные числа", ButtonType.OK);
                alert.showAndWait();
            }

            String userChoiseLifeT = view.getFemaleLifeTimeTextField().getText();
            try {
                int n = Integer.parseInt(userChoiseLifeT);
                if (n < 0) {
                    throw new NumberFormatException();
                }
                FemaleStudent.setLiveTime(n);
            } catch (NumberFormatException e) {
                FemaleStudent.setLiveTime(5);
                view.getFemaleLifeTimeTextField().setText("5");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ВРЕМЕНИ ЖИЗНИ студентки. Разрешено вводить только целые положительные числа", ButtonType.OK);
                alert.showAndWait();
            }
        });
        view.getApplyMaleProp().setOnAction(event -> {
            String userChoiseP = (String) view.getMaleSpawnProbabilityBox().getSelectionModel().getSelectedItem();
            if (userChoiseP == null)
                userChoiseP = "null";

            for (Map.Entry<String, Float> entry : view.getComboBoxMap().entrySet()) {
                String key = entry.getKey();
                Float value = entry.getValue();
                if (userChoiseP.equals(key)) {
                    model.setMaleStudentP(value);
                }
            }

            String userChoiseN = view.getMaleSpawnTimeTextField().getText();
            try {
                int n = Integer.parseInt(userChoiseN);
                if (n < 0) {
                    throw new NumberFormatException();
                }
                model.setMaleStudentN(n);
            } catch (NumberFormatException e) {
                model.setMaleStudentN(2);
                view.getMaleSpawnTimeTextField().setText("2");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ПЕРИОДА РОЖДЕНИЯ студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
                alert.showAndWait();
            }

            String userChoiseLifeT = view.getMaleLifeTimeTextField().getText();
            try {
                int n = Integer.parseInt(userChoiseLifeT);
                if (n < 0) {
                    throw new NumberFormatException();
                }
                MaleStudent.setLiveTime(n);
            } catch (NumberFormatException e) {
                MaleStudent.setLiveTime(4);
                view.getMaleLifeTimeTextField().setText("4");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ВРЕМЕНИ ЖИЗНИ студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
                alert.showAndWait();
            }
        });
        view.getInformationCheckBox().setOnAction(event -> {
            if (view.getInformationCheckBox().isSelected()) {
                model.setInformationWindowFlag(true);
            } else {
                model.setInformationWindowFlag(false);
            }
        });
        view.getLiveObjButton().setOnAction(event -> model.pauseGeneration("liveObjWindow.fxml", "Текущие объекты"));
        view.getAutorsMenuItem().setOnAction(event -> model.autorsWindow());
        view.getMaleAIButton().setOnAction(event -> {
            if (model.maleAI.paused){
                model.beginMaleAI();
            } else{
                model.pauseMaleAI();
            }
        });
        view.getFemaleAIButton().setOnAction(event -> {
            if (model.femaleAI.paused){
                model.beginFemaleAI();
            } else{
                model.pauseFemaleAI();
            }
        });
    }

    private void startFunk() {
        model.startGeneration();
        view.getButtonStart().setDisable(true);
        view.getButtonStop().setDisable(false);
        view.getApplyMaleProp().setDisable(true);
        view.getMaleSpawnTimeTextField().setDisable(true);
        view.getMaleSpawnProbabilityBox().setDisable(true);
        view.getApplyFemaleProp().setDisable(true);
        view.getFemaleSpawnTimeTextField().setDisable(true);
        view.getFemaleSpawnProbabilityBox().setDisable(true);
        view.getMaleLifeTimeTextField().setDisable(true);
        view.getFemaleLifeTimeTextField().setDisable(true);
        view.getLiveObjButton().setDisable(false);
        view.getStartMenuItem().setDisable(true);
        view.getStopMenuItem().setDisable(false);
    }

    private void stopFunk() {
        if (model.isStartFlag()) {
            if (model.isInformationWindowFlag()) {
                //model.pauseGeneration("infModalWindow.fxml", "Статистика");
                model.pauseGeneration("infModalWindow.fxml", "Статистика");
            } else {
                model.stopGeneration();
            }
            if (!model.isStartFlag()) {
                view.getButtonStart().setDisable(false);
                view.getButtonStop().setDisable(true);
                view.getLiveObjButton().setDisable(true);
            }
        }
        view.getApplyMaleProp().setDisable(false);
        view.getMaleSpawnTimeTextField().setDisable(false);
        view.getMaleSpawnProbabilityBox().setDisable(false);
        view.getApplyFemaleProp().setDisable(false);
        view.getFemaleSpawnTimeTextField().setDisable(false);
        view.getFemaleSpawnProbabilityBox().setDisable(false);
        view.getMaleLifeTimeTextField().setDisable(false);
        view.getFemaleLifeTimeTextField().setDisable(false);
        view.getStartMenuItem().setDisable(false);
        view.getStopMenuItem().setDisable(true);
    }
}
