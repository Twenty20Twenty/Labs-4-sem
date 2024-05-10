package ru.nstu.javafx_labs_lipatov_v2.data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

public class SerializableImageView implements Serializable {
    transient ImageView imageView;
    transient String path;

    public SerializableImageView(Image image, String path) {
        this.imageView = new ImageView(image);
        this.path = path;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        out.writeObject(path);
        out.writeObject(imageView.getX());
        out.writeObject(imageView.getY());
        out.writeObject(imageView.getFitHeight());
        out.writeObject(imageView.getFitWidth());
        out.writeObject(imageView.isPreserveRatio());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.path = (String) in.readObject();
        imageView = new ImageView();
        this.imageView.setImage(new Image(new FileInputStream(path)));
        this.imageView.setX((Double) in.readObject());
        this.imageView.setY((Double) in.readObject());
        this.imageView.setFitHeight((Double) in.readObject());
        this.imageView.setFitWidth((Double) in.readObject());
        this.imageView.setPreserveRatio((Boolean) in.readObject());
    }
}