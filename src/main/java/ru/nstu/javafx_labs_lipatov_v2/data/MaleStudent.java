package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MaleStudent extends Student {
    public static int countMaleStudent = 0;
    public static Image image;
    public static int lifeTime = 4;
    public double speed = 1;
    private long time;

    public static void setLifeTime(int lifeTime) {
        MaleStudent.lifeTime = lifeTime;
    }

    static {
        try{
            image = new Image(new FileInputStream("src/main/resources/ru/nstu/javafx_labs_lipatov_v2/MaleStudent.png"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public MaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y);
        imageView.setImage(image);
        countMaleStudent++;
    }

    @Override
    public void paint(){
        imageView.setX(getX());
        imageView.setY(getY());
    }

    private boolean firstTime = true;
    int direction1;
    int direction2;
    @Override
    public void move(){
        double newX;
        double newY;
        Random rand = new Random();
        if (firstTime){
            time = System.currentTimeMillis();
            direction1 = rand.nextInt(0, 2);
            direction2 = rand.nextInt(1, 3);
            firstTime = false;
        }
        if (System.currentTimeMillis() - time >= 1000){
            time = System.currentTimeMillis();
            direction1 = rand.nextInt(0, 2);
            direction2 = rand.nextInt(1, 3);
        }
        switch (direction1){
            case 0:
                newX = getX();
                newY = getY() + 1 * speed * Math.pow(-1, direction2);
                setX(newX);
                setY(newY);
                return;
            case 1:
                newX = getX() + 1 * speed * Math.pow(-1, direction2);
                newY = getY();
                setX(newX);
                setY(newY);
        }
    }
}
