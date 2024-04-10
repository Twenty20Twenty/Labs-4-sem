package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.util.UUID;


public abstract class Student implements IBehaviour {
    private int posX;
    private int posY;
    final ImageView imageView;

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
