package ru.nstu.javafx_labs_lipatov;

import javafx.application.Platform;
import ru.nstu.javafx_labs_lipatov.objects.FemaleStudent;
import ru.nstu.javafx_labs_lipatov.objects.MaleStudent;
import ru.nstu.javafx_labs_lipatov.objects.Student;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Habitat {
    private static volatile Habitat instance;
    private static int width = 600;
    private static int height = 600;
    private ArrayList<Student> array = new ArrayList<Student>();
    private Controller controller;
    private float p1;
    private float p2;
    private int n1;
    private int n2;
    private boolean startFlag;
    private boolean informationFlag = true;
    public boolean timeFlag = true;
    private boolean statisticFlag;
    private int seconds;
    private int minutes;
    private Timer timer;
    private long startTime;

    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return height;
    }
    public static void setInstance(Habitat instance) {
        Habitat.instance = instance;
    }
    public Habitat(Controller controller){
        this.controller = controller;
    }

    public boolean isStartFlag() {
        return startFlag;
    }

    public boolean isInformationFlag() {
        return informationFlag;
    }

    public void setInformationFlag(boolean informationFlag) {
        this.informationFlag = informationFlag;
    }

    public void setStatisticFlag(boolean statisticFlag) {
        this.statisticFlag = statisticFlag;
    }

    public void setMaleStudent(int n, float p){
        n1 = n;
        p1 = p;
    }
    public void setFemaleStudent(int n, float p){
        n2 = n;
        p2 = p;
    }

    public void startGeneration(){
        MaleStudent.countMaleStudent = 0;
        FemaleStudent.countFemaleStudent = 0;
        startFlag = true;
        statisticFlag = false;
        seconds = 0;
        minutes = 0;
        timer = new Timer();
        startTime = System.currentTimeMillis();
        showStatisticLabel();
        startWork();
    }

    public void stopGeneration(){
        startFlag = false;
        statisticFlag = true;
        showStatisticLabel();
        if(!startFlag){
            timer.cancel();
            clearList();
        }
    }
    private int iter = 0;
    private void startWork(){
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                iter++;
                if (iter % 20 == 0){
                    seconds++;
                }
                if (seconds == 60){
                    minutes++;
                    seconds = 0;
                }

                Platform.runLater(() -> {
                    updateTimer();
                    if (iter % 20 == 0){
                        update(System.currentTimeMillis() - startTime);
                    }
                });

            }
        }, 0, 50);
    }

    public void update(long time){
        if (startFlag){
            create(time/1000);
        }
    }

    private void create(long time){
        Random random = new Random();
        float p = random.nextFloat();
        try {
            if ((time % n1 == 0) && (p1 <= p)){
                MaleStudent student = new MaleStudent(random.nextInt(10, 550), random.nextInt(35, 300));
                controller.getVisualPane().getChildren().add(student.getImageView());
                array.add(student);
            }
            if ((time % n2 == 0) && (p2 <= p)){
                FemaleStudent student = new FemaleStudent(random.nextInt(10, 550), random.nextInt(35, 300));
                controller.getVisualPane().getChildren().add(student.getImageView());
                array.add(student);
            }
        }
        catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    private void clearList(){
        array.forEach((tmp) -> controller.getVisualPane().getChildren().remove(tmp.getImageView()));
        array.clear();
    }

    public void showStatisticLabel(){
        if (informationFlag) {
            if (statisticFlag) {
                String statistic = "Создано студентов: " + MaleStudent.countMaleStudent +
                        "\nСоздано студенток: " + FemaleStudent.countFemaleStudent;
                statistic += "\nВремя работы: " + (System.currentTimeMillis() - startTime)/1000 + "(сек)";
                controller.getStatisticLabel().setText(statistic);
                controller.getStatisticLabel().setVisible((true));
            }
            else{
                controller.getStatisticLabel().setVisible((false));
                controller.getStatisticLabel().setText("");
            }
        }
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
    public void showTimer(){
        timeFlag = !timeFlag;
        if (timeFlag) {
            controller.getLabelTextTIMER().setVisible(true);
            controller.getLabelTimer().setVisible(true);
            controller.getRadioButtonShowTimer().setSelected(true);
        }
        else {
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
