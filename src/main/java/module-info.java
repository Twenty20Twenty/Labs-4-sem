module com.example.javafx_labs_lipatov {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nstu.javafx_labs_lipatov to javafx.fxml;
    exports ru.nstu.javafx_labs_lipatov;
}