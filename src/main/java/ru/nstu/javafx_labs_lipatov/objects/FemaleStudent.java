package ru.nstu.javafx_labs_lipatov.objects;

import javafx.scene.image.Image;
import ru.nstu.javafx_labs_lipatov.IBehaviour;
import ru.nstu.javafx_labs_lipatov.objects.Student;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FemaleStudent extends Student implements IBehaviour {
    public static int countFemaleStudent = 0;
    public static Image image;
    public FemaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y,"src/main/resources/ru/nstu/javafx_labs_lipatov/FemaleStudent.png");
        if (countFemaleStudent == 0)
            image = new Image(new FileInputStream(this.getPath()));
        imageView.setImage(image);
        countFemaleStudent++;
    }
}
