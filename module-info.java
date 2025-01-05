module org.example.javanetworking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;

    opens org.example.javanetworking to javafx.fxml;
    exports org.example.javanetworking;
}