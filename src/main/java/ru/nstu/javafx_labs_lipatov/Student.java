package ru.nstu.javafx_labs_lipatov;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public abstract class Student {
    private String path;
    final ImageView imageView;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public ImageView getImageView(){
        return imageView;
    }

    public Student(int _x, int _y, String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        imageView = new ImageView(image);
        imageView.setX(_x);
        imageView.setY(_y);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        }
}
