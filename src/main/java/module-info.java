module com._2048 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com._2048 to javafx.fxml;
    exports com._2048;
}