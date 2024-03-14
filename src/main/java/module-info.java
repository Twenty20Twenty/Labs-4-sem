module com.example.javafx_labs_lipatov {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nstu.javafx_labs_lipatov to javafx.fxml;
    exports ru.nstu.javafx_labs_lipatov;
    exports ru.nstu.javafx_labs_lipatov.objects;
    opens ru.nstu.javafx_labs_lipatov.objects to javafx.fxml;
}