package ru.nstu.javafx_labs_lipatov;

import java.io.FileNotFoundException;

public class MaleStudent extends Student implements IBehaviour{
    public static int countMaleStudent = 0;
    public MaleStudent(int _x, int _y) throws FileNotFoundException {
        super(_x,_y,"src/main/resources/ru/nstu/javafx_labs_lipatov/MaleStudent.png");
        countMaleStudent++;
    }
}
