module ru.nstu.javafx_labs_lipatov_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports ru.nstu.javafx_labs_lipatov_v2;
    opens ru.nstu.javafx_labs_lipatov_v2 to javafx.fxml;
    exports ru.nstu.javafx_labs_lipatov_v2.data;
    opens ru.nstu.javafx_labs_lipatov_v2.data to javafx.fxml;
    exports ru.nstu.javafx_labs_lipatov_v2.mvc;
    opens ru.nstu.javafx_labs_lipatov_v2.mvc to javafx.fxml;
}