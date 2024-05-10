package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.Image;
import ru.nstu.javafx_labs_lipatov_v2.MainLauncher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FemaleStudent extends Student{
    public static int countFemaleStudent = 0;
    public static Image image;
    public static int lifeTime = 5;
    private double angle = 0.1;
    public double speed = 10;
    public double radius = 4;
    private static String path = "FemaleStudent.png";


    public static void setLiveTime(int liveTime) {
        FemaleStudent.lifeTime = liveTime;
    }

    static {
        image = new Image(MainLauncher.class.getResourceAsStream(path));
//        try{
//            image = new Image(new FileInputStream(path));
//        } catch(FileNotFoundException e){
//            e.printStackTrace();
//        }
    }

    public FemaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y, image, path);
        countFemaleStudent++;
    }

    @Override
    public void paint(){
        imageView.imageView.setX(getX());
        imageView.imageView.setY(getY());
    }

    @Override
    public void move(){
        double newX = this.getX() + (radius * Math.cos(this.getAngle()));
        double newY = this.getY() + (radius * Math.sin(this.getAngle()));
        this.setAngle(this.getAngle() + speed / 100);

        setX(newX);
        setY(newY);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
