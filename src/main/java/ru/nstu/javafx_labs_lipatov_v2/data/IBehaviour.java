package ru.nstu.javafx_labs_lipatov_v2.data;

public interface IBehaviour {
    void setX(int x);
    void setY(int y);
    int getX();
    int getY();
    void move(int X, int Y);
}