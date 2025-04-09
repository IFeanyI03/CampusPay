module com.example.campusPayApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.campusPayApp to javafx.fxml;
    opens com.example.campusPayApp.ui to javafx.fxml;
    opens com.example.campusPayApp.ui.auth to javafx.fxml;
    opens com.example.campusPayApp.ui.job to javafx.fxml;
    opens com.example.campusPayApp.ui.profile to javafx.fxml;
    opens com.example.campusPayApp.utils to javafx.fxml;
    exports com.example.campusPayApp;
}