package ru.nstu.javafx_labs_lipatov.objects;

import javafx.scene.image.Image;
import ru.nstu.javafx_labs_lipatov.IBehaviour;
import ru.nstu.javafx_labs_lipatov.objects.Student;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MaleStudent extends Student implements IBehaviour {
    public static int countMaleStudent = 0;
    public static Image image;

    static {
        try{
            image = new Image(new FileInputStream("src/main/resources/ru/nstu/javafx_labs_lipatov/MaleStudent.png"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public MaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y);
        imageView.setImage(image);
        countMaleStudent++;
    }
}
