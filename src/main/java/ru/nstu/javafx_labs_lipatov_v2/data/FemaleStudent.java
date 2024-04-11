package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FemaleStudent extends Student{
    public static int countFemaleStudent = 0;
    public static Image image;
    public static int liveTime = 5;


    public static void setLiveTime(int liveTime) {
        FemaleStudent.liveTime = liveTime;
    }

    static {
        try{
            image = new Image(new FileInputStream("src/main/resources/ru/nstu/javafx_labs_lipatov_v2/FemaleStudent1.png"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public FemaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y);
        imageView.setImage(image);
        countFemaleStudent++;
    }

    @Override
    public void paint(){
        System.out.println("FemalePrint");
        imageView.setX(getX());
        imageView.setY(getY());
    }

    @Override
    public void move(int X, int Y){
        setX(X);
        setY(Y);
    }
}
