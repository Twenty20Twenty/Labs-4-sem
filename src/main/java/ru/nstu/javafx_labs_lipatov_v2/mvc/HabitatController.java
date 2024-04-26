package ru.nstu.javafx_labs_lipatov_v2.mvc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nstu.javafx_labs_lipatov_v2.UserConsole;
import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.MaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class HabitatController {
    private final HabitatView view;
    private final HabitatModel model;
    private Properties properties;

    Properties defaultProperties() {
        Properties prop = new Properties();
        prop.setProperty("spawnMale", "2");
        prop.setProperty("lifeMale", "4");
        prop.setProperty("probobalityMale", "50");
        prop.setProperty("priorityMale", "5");

        prop.setProperty("spawnFemale", "3");
        prop.setProperty("lifeFemale", "5");
        prop.setProperty("probobalityFemale", "80");
        prop.setProperty("priorityFemale", "5");
        return prop;
    }

    public HabitatController(HabitatView view, HabitatModel model) {
        this.view = view;
        this.model = model;
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("src/main/resources/ru/nstu/javafx_labs_lipatov_v2/Application.properties")));
        } catch (FileNotFoundException e) {
            try {
                defaultProperties().store(new FileOutputStream(new File("src/main/resources/ru/nstu/javafx_labs_lipatov_v2/Application.properties")), "Config");
                properties = defaultProperties();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    void setProperties(Properties properties) {
        model.setMaleStudentN(Integer.parseInt(properties.getProperty("spawnMale")));
        view.getMaleSpawnTimeTextField().setText(String.valueOf(model.getTimeMale()));

        model.setFemaleStudentN(Integer.parseInt(properties.getProperty("spawnFemale")));
        view.getFemaleSpawnTimeTextField().setText(String.valueOf(model.getTimeFemale()));

        MaleStudent.lifeTime = Integer.parseInt(properties.getProperty("lifeMale"));
        view.getMaleLifeTimeTextField().setText(String.valueOf(MaleStudent.lifeTime));

        FemaleStudent.lifeTime = Integer.parseInt(properties.getProperty("lifeFemale"));
        view.getFemaleLifeTimeTextField().setText(String.valueOf(FemaleStudent.lifeTime));

        model.setMaleStudentP(1 - Integer.parseInt(properties.getProperty("probobalityMale")) / 100);
        view.getMaleSpawnProbabilityBox().getSelectionModel().select(String.valueOf(properties.getProperty("probobalityMale")) + " %");

        model.setFemaleStudentP(1 - Integer.parseInt(properties.getProperty("probobalityFemale")) / 100);
        view.getFemaleSpawnProbabilityBox().getSelectionModel().select(String.valueOf(properties.getProperty("probobalityFemale")) + " %");

        view.getPriorityMaleComboBox().getSelectionModel().select(properties.getProperty("priorityMale"));
        model.maleAI.setPriority(Integer.parseInt(properties.getProperty("priorityMale")));

        view.getPriorityFemaleComboBox().getSelectionModel().select(properties.getProperty("priorityFemale"));
        model.femaleAI.setPriority(Integer.parseInt(properties.getProperty("priorityFemale")));

        /*
        maleSpawnTimeTextField.setText("2");
        maleSpawnProbability.getSelectionModel().select("50 %");
        maleLifeTimeTextField.setText("4");
        priorityMaleComboBox.getSelectionModel().select("5");

        femaleSpawnTimeTextField.setText("3");
        femaleSpawnProbability.getSelectionModel().select("80 %");
        femaleLifeTimeTextField.setText("5");
        priorityFemaleComboBox.getSelectionModel().select("5");
        */
    }

    public void saveProperties() {
        properties.setProperty("spawnMale", view.getMaleSpawnTimeTextField().getText());
        properties.setProperty("spawnFemale", view.getFemaleSpawnTimeTextField().getText());
        properties.setProperty("lifeMale", view.getMaleLifeTimeTextField().getText());
        properties.setProperty("lifeFemale", view.getFemaleLifeTimeTextField().getText());

        properties.setProperty("priorityMale", (String) view.getPriorityMaleComboBox().getSelectionModel().getSelectedItem());
        properties.setProperty("priorityFemale", (String) view.getPriorityFemaleComboBox().getSelectionModel().getSelectedItem());

        properties.setProperty("probobalityMale", String.valueOf(view.getMaleSpawnProbabilityBox().getSelectionModel().getSelectedItem()).replaceAll(" %", ""));
        properties.setProperty("probobalityFemale", String.valueOf(view.getFemaleSpawnProbabilityBox().getSelectionModel().getSelectedItem()).replaceAll(" %", ""));

        try {
            properties.store(new FileOutputStream(new File("src/main/resources/ru/nstu/javafx_labs_lipatov_v2/Application.properties")), "Config");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void init() {
        setProperties(properties);

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
            if (model.isStartFlag()) {
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

            saveProperties();
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

        view.getAuthorsMenuItem().setOnAction(event -> model.authorsWindow());

        view.getLiveObjMenuItem().setOnAction(event -> model.pauseGeneration("liveObjWindow.fxml", "Текущие объекты"));

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
                MaleStudent.setLifeTime(n);
            } catch (NumberFormatException e) {
                MaleStudent.setLifeTime(4);
                view.getMaleLifeTimeTextField().setText("4");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректный ввод ВРЕМЕНИ ЖИЗНИ студента. Разрешено вводить только целые положительные числа", ButtonType.OK);
                alert.showAndWait();
            }
        });

        view.getInformationCheckBox().setOnAction(event -> model.setInformationWindowFlag(view.getInformationCheckBox().isSelected()));

        view.getLiveObjButton().setOnAction(event -> model.pauseGeneration("liveObjWindow.fxml", "Текущие объекты"));

        view.getMaleAIButton().setOnAction(event -> {
            if (model.maleAI.paused) {
                model.beginMaleAI();
            } else {
                model.pauseMaleAI();
            }
        });

        view.getFemaleAIButton().setOnAction(event -> {
            if (model.femaleAI.paused) {
                model.beginFemaleAI();
            } else {
                model.pauseFemaleAI();
            }
        });

        view.getPriorityMaleComboBox().setOnAction(event -> {
            model.maleAI.setPriority(Integer.parseInt((String.valueOf(view.getPriorityMaleComboBox().getValue()))));
            System.out.println("maleAI priority is: " + model.maleAI.getPriority());
        });

        view.getPriorityFemaleComboBox().setOnAction(event -> {
            model.femaleAI.setPriority(Integer.parseInt((String.valueOf(view.getPriorityFemaleComboBox().getValue()))));
            System.out.println("femaleAI priority is: " + model.femaleAI.getPriority());
        });

        view.getButtonConsole().setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(UserConsole.class.getResource("console.fxml"));
                Parent root = loader.load();
                UserConsole console = loader.getController();
                console.parentModel = model;
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                console.getConsoleText().setText(">> ");
                console.getConsoleText().end();
                stage.setScene(scene);
                stage.setMaximized(false);
                stage.setResizable(false);
                stage.setTitle("Консоль");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
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
        view.getLiveObjMenuItem().setDisable(false);
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
                view.getLiveObjMenuItem().setDisable(true);
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
