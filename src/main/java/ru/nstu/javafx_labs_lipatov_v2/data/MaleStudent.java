package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.Image;
import ru.nstu.javafx_labs_lipatov_v2.MainLauncher;
import java.io.FileNotFoundException;
import java.util.Random;

public class MaleStudent extends Student {
    public static int countMaleStudent = 0;
    public static Image image;
    public static int lifeTime = 4;
    public double speed = 1;
    private long time;
    private static String path = "MaleStudent.png";
    public static void setLifeTime(int lifeTime) {
        MaleStudent.lifeTime = lifeTime;
    }

    static {
            image = new Image(MainLauncher.class.getResourceAsStream(path));
    }

    public MaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y, image, path);
        countMaleStudent++;
    }

    @Override
    public void paint(){
        imageView.imageView.setX(getX());
        imageView.imageView.setY(getY());
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
