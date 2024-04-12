package ru.nstu.javafx_labs_lipatov_v2.mvc;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nstu.javafx_labs_lipatov_v2.AI.FemaleAI;
import ru.nstu.javafx_labs_lipatov_v2.AI.MaleAI;
import ru.nstu.javafx_labs_lipatov_v2.ModalWindow;
import ru.nstu.javafx_labs_lipatov_v2.data.FemaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.MaleStudent;
import ru.nstu.javafx_labs_lipatov_v2.data.Student;
import ru.nstu.javafx_labs_lipatov_v2.data.StudentCollections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HabitatModel {
    private Timer timer;
    private double pMale;
    private double pFemale;
    private int timeMale;
    private int timeFemale;
    private long startTime;
    private long pauseTime;
    private int seconds;
    private int minutes;
    private boolean startFlag = false;
    private boolean informationWindowFlag = true;
    public boolean timeFlag = true;
    HabitatView view;
    public MaleAI maleAI = new MaleAI();
    public FemaleAI femaleAI = new FemaleAI();

    public HabitatModel(double pMale, double pFemale, int timeMale, int timeFemale, HabitatView view) {
        this.pMale = pMale;
        this.pFemale = pFemale;
        this.timeMale = timeMale;
        this.timeFemale = timeFemale;
        this.view = view;
        maleAI.start();
        femaleAI.start();
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
        beginMaleAI();
        beginFemaleAI();
        startWork();
    }

    public void pauseGeneration(String fxmlLoader, String title) {
        pauseTime = System.currentTimeMillis();
        timer.cancel();
        String text = new String();
        switch (title) {
            case "Текущие объекты":
                text = StudentCollections.getInstance().getLiveObjString().toString();
                break;
            case "Статистика":
                text = getStatistic();
                break;
        }
        try {
            FXMLLoader loader = new FXMLLoader(ModalWindow.class.getResource(fxmlLoader));
            Parent root = loader.load();
            ModalWindow modalWindow = new ModalWindow();
            ModalWindow modalController = loader.getController();
            modalController.model = this;
            modalController.setText(text);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle(title);
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
            StudentCollections.getInstance().clearCollections(view);
        }
        lastTimeM = -100;
        lastTimeF = -100;
        pauseMaleAI();
        pauseFemaleAI();
    }

    private long secStart;

    private void startWork() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds = (int) (System.currentTimeMillis() - secStart) / 1000;
                if ((seconds % 60 == 0) && (seconds != 0)) {
                    minutes++;
                    secStart = System.currentTimeMillis();
                    seconds = 0;
                }

                Platform.runLater(() -> {
                    updateTimer();
                    update(System.currentTimeMillis() - startTime);
                    synchronized (StudentCollections.getInstance().linkedStudentList){
                        for (Student stud: StudentCollections.getInstance().linkedStudentList){
                            stud.paint();
                        }
                    }
                });

            }
        }, 0, 10);
    }

    public void update(long time) {
        if (startFlag) {
            synchronized (StudentCollections.getInstance().linkedStudentList){
                create(time/1000);
                StudentCollections.getInstance().updateCollections(time, view);
            }
        }
    }
    private long lastTimeM = -100;
    private long lastTimeF = -100;
    private void create(long time) {
        Random random = new Random();
        float p = random.nextFloat();
        try {
            if (time != lastTimeM) {
                lastTimeM = time;
                if ((time % timeMale == 0) && (pMale <= p)) {
                    MaleStudent student = new MaleStudent(random.nextInt(10, 750), random.nextInt(25, 430));
                    view.getVisualPane().getChildren().add(student.getImageView());
                    StudentCollections.getInstance().linkedStudentList.add(student);
                    StudentCollections.getInstance().idHashSet.add(student.getId());
                    StudentCollections.getInstance().bornTreeMap.put(student.getId(), time * 1000);
                }
            }
            if (time != lastTimeF) {
                lastTimeF = time;
                if ((time % timeFemale == 0) && (pFemale <= p) ) {
                    FemaleStudent student = new FemaleStudent(random.nextInt(10, 750), random.nextInt(25, 430));
                    view.getVisualPane().getChildren().add(student.getImageView());
                    StudentCollections.getInstance().linkedStudentList.add(student);
                    StudentCollections.getInstance().idHashSet.add(student.getId());
                    StudentCollections.getInstance().bornTreeMap.put(student.getId(), time * 1000);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getStatistic() {
        String statistic = "Создано студентов: " + MaleStudent.countMaleStudent + "\nСоздано студенток: " + FemaleStudent.countFemaleStudent;
        statistic += "\nВремя симуляции: " + (System.currentTimeMillis() - startTime) + " (мс)";
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
        view.getLabelTimer().setText(time);
        if (timeFlag) {
            view.getLabelTimer().setVisible(true);
            view.getLabelTextTIMER().setVisible(true);
        }
    }

    public void showTimer() {
        timeFlag = !timeFlag;
        if (timeFlag) {
            view.getLabelTextTIMER().setVisible(true);
            view.getLabelTimer().setVisible(true);
            view.getRadioButtonShowTimer().setSelected(true);
            view.getShowTimeMenuItem().setDisable(true);
            view.getHideTimeMenuItem().setDisable(false);
        } else {
            view.getLabelTextTIMER().setVisible(false);
            view.getLabelTimer().setVisible(false);
            view.getRadioButtonHideTimer().setSelected(true);
            view.getShowTimeMenuItem().setDisable(false);
            view.getHideTimeMenuItem().setDisable(true);
        }
    }

    public void authorsWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(ModalWindow.class.getResource("authorsWindow.fxml"));
            Parent root = loader.load();
            ModalWindow modalWindow = new ModalWindow();
            ModalWindow modalController = loader.getController();
            modalController.model = this;
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle("Авторы");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseMaleAI(){
        if (!maleAI.paused){
            maleAI.paused = true;
        }
    }

    public void beginMaleAI(){
        if (maleAI.paused){
            synchronized (maleAI.obj){
                maleAI.paused = false;
                maleAI.flagEnd = false;
                maleAI.obj.notify();
            }
        }
    }

    public void pauseFemaleAI(){
        if (!femaleAI.paused){
            femaleAI.paused = true;
        }
    }

    public void beginFemaleAI(){
        if (femaleAI.paused){
            synchronized (femaleAI.obj){
                femaleAI.paused = false;
                femaleAI.flagEnd = false;
                femaleAI.obj.notify();
            }
        }
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

    public HabitatView getView() {
        return view;
    }

    public double getpMale() {
        return pMale;
    }

    public MaleAI getMaleAI() {
        return maleAI;
    }

    public FemaleAI getFemaleAI() {
        return femaleAI;
    }
}
