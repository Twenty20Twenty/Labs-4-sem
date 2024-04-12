package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.util.UUID;


public abstract class Student implements IBehaviour {
    private double posX;
    private double posY;
    public ImageView imageView;

    public abstract void paint();

    public ImageView getImageView() {
        return imageView;
    }

    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    private void checkId() {
        for (UUID i : StudentCollections.getInstance().idHashSet) {
            if (this.id == i) {
                this.id = UUID.randomUUID();
                checkId();
            }
        }
    }

    public Student(int _x, int _y) throws FileNotFoundException {
        this.posX = _x;
        this.posY = _y;
        checkId();
        StudentCollections.getInstance().idHashSet.add(this.id);
        imageView = new ImageView();
        imageView.setX(posX);
        imageView.setY(posY);
        imageView.setFitHeight(140);
        imageView.setFitWidth(140);
        imageView.setPreserveRatio(true);
    }

    @Override
    public double getX() {
        return posX;
    }
    @Override
    public void setX(double posX) {
        this.posX = posX;
    }
    @Override
    public double getY() {
        return posY;
    }
    @Override
    public void setY(double posY) {
        this.posY = posY;
    }
}
