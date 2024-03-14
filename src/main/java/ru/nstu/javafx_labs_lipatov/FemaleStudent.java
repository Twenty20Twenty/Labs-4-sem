package ru.nstu.javafx_labs_lipatov;

import java.io.FileNotFoundException;

public class FemaleStudent extends Student implements IBehaviour{
    public static int countFemaleStudent = 0;
    public FemaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y,"src/main/resources/ru/nstu/javafx_labs_lipatov/FemaleStudent.png");
        countFemaleStudent++;
    }
}
