package ru.nstu.javafx_labs_lipatov;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nstu.javafx_labs_lipatov.Controller.Controller;
import ru.nstu.javafx_labs_lipatov.Controller.InformationModalWindow;
import ru.nstu.javafx_labs_lipatov.objects.FemaleStudent;
import ru.nstu.javafx_labs_lipatov.objects.MaleStudent;
import ru.nstu.javafx_labs_lipatov.objects.Student;
import ru.nstu.javafx_labs_lipatov.objects.StudentCollections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Habitat {
    private static volatile Habitat instance;
    private static int width = 600;
    private static int height = 600;
    private LinkedList<Student> listObjects = new LinkedList<Student>();
    private Controller controller;
    private float pMale;
    private float pFemale;
    private int timeMale;
    private int timeFemale;
    private boolean startFlag;
    private boolean informationWindowFlag = true;
    public boolean timeFlag = true;
    private int seconds;
    private int minutes;
    private Timer timer;
    private long startTime;
    private long pauseTime;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setInstance(Habitat instance) {
        Habitat.instance = instance;
    }

    public Habitat(Controller controller) {
        this.controller = controller;
    }

    public boolean isStartFlag() {
        return startFlag;
    }

    public boolean isInformationWindowFlag() {
        return informationWindowFlag;
    }

    public void setInformationWindowFlag(boolean informationFlag) {
        this.informationWindowFlag = informationFlag;
    }

    public Controller getController() {
        return controller;
    }

    public void setMaleStudentP(float p) {
        pMale = p;
    }

    public void setMaleStudentN(int n) {
        timeMale = n;
    }

    public void setFemaleStudentP(float p) {
        pFemale = p;
    }

    public void setFemaleStudentN(int n) {
        timeFemale = n;
    }

    public Timer getTimer() {
        return timer;
    }

    public void startGeneration() {
        MaleStudent.countMaleStudent = 0;
        FemaleStudent.countFemaleStudent = 0;
        startFlag = true;
        seconds = -1;
        minutes = 0;
        timer = new Timer();
        startTime = System.currentTimeMillis();
        secStart = startTime;
        startWork();
    }

    public void pauseGeneration() {
        pauseTime = System.currentTimeMillis();
        timer.cancel();
        String statistic = "Создано студентов: " + MaleStudent.countMaleStudent + "\nСоздано студенток: " + FemaleStudent.countFemaleStudent;
        statistic += "\nВремя симуляции: " + (System.currentTimeMillis() - startTime) + " (мс)";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modalWindow.fxml"));
            Parent root = loader.load();
            InformationModalWindow modalController = loader.getController();
            modalController.parentController = controller;
            modalController.setText(statistic);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle("Статистика");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unPauseGeneration() {
        startFlag = true;
        timer = new Timer();
        startTime += (System.currentTimeMillis() - pauseTime);
        secStart += (System.currentTimeMillis() - pauseTime);
        startWork();
    }

    public void stopGeneration() {
        startFlag = false;
        if (!startFlag) {
            timer.cancel();
            StudentCollections.getInstance().clearCollections(controller);
        }
    }

    private long secStart;

    private void startWork() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if ((seconds % 60 == 0) && (seconds != 0)) {
                    minutes++;
                    secStart = System.currentTimeMillis();
                    seconds = 0;
                }
                seconds = (int) (System.currentTimeMillis() - secStart) / 1000;
                Platform.runLater(() -> {
                    updateTimer();
                    update(System.currentTimeMillis() - startTime);
                });

            }
        }, 0, 1000);
    }

    public void update(long time) {
        if (startFlag) {
            create(time / 1000);
            StudentCollections.getInstance().updateCollections(time / 1000, controller);
        }
    }

    private void create(long time) {
        Random random = new Random();
        float p = random.nextFloat();
        try {
            if ((time % timeMale == 0) && (pMale <= p)) {
                MaleStudent student = new MaleStudent(random.nextInt(10, 550), random.nextInt(35, 300 - 50));
                controller.getVisualPane().getChildren().add(student.getImageView());
                StudentCollections.getInstance().linkedStudentList.add(student);
                StudentCollections.getInstance().idHashSet.add(student.getId());
                StudentCollections.getInstance().bornTreeMap.put(student.getId(), time);
            }
            if ((time % timeFemale == 0) && (pFemale <= p)) {
                FemaleStudent student = new FemaleStudent(random.nextInt(10, 550), random.nextInt(35, 300 - 50));
                controller.getVisualPane().getChildren().add(student.getImageView());
                StudentCollections.getInstance().linkedStudentList.add(student);
                StudentCollections.getInstance().idHashSet.add(student.getId());
                StudentCollections.getInstance().bornTreeMap.put(student.getId(), time);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getStatistic() {
        String statistic = "Создано студентов: " + MaleStudent.countMaleStudent +
                "\nСоздано студенток: " + FemaleStudent.countFemaleStudent;
        statistic += "\nВремя работы: " + (System.currentTimeMillis() - startTime) / 1000 + "(сек)";
        return statistic;
    }

    public void updateTimer() {
        String min = minutes + "";
        String sec = seconds + "";
        if (min.length() < 2)
            min = "0" + min;
        if (sec.length() < 2)
            sec = ("0" + sec);
        String time = min + ":" + sec;
        controller.getLabelTimer().setText(time);
        if (timeFlag) {
            controller.getLabelTimer().setVisible(true);
            controller.getLabelTextTIMER().setVisible(true);
        }
    }

    public void showTimer() {
        timeFlag = !timeFlag;
        if (timeFlag) {
            controller.getLabelTextTIMER().setVisible(true);
            controller.getLabelTimer().setVisible(true);
            controller.getRadioButtonShowTimer().setSelected(true);
        } else {
            controller.getLabelTextTIMER().setVisible(false);
            controller.getLabelTimer().setVisible(false);
            controller.getRadioButtonHideTimer().setSelected(true);
        }
    }

    public static Habitat getInstance() {
        Habitat localInstance = instance;
        if (localInstance == null) {
            synchronized (Habitat.class) {
                localInstance = instance;
            }
        }
        return localInstance;
    }
}
