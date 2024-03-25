package ru.nstu.javafx_labs_lipatov.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;


public abstract class Student {
    private int posX;
    private int posY;
    final ImageView imageView;
    public ImageView getImageView(){
        return imageView;
    }
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    public Student(int _x, int _y) throws FileNotFoundException {
        this.posX = _x;
        this.posY = _y;
        imageView = new ImageView();
        imageView.setX(posX);
        imageView.setY(posY);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
    }

    public int getX() {
        return posX;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    public int getY() {
        return posY;
    }

    public void setY(int posY) {
        this.posY = posY;
    }
}
