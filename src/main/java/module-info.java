module com.jets {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.jets to javafx.fxml;
    exports com.jets;
}