module at.tiefenbrunner.swen2semesterprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires jakarta.annotation;
    requires java.desktop;
    requires static lombok;
    requires org.apache.logging.log4j;
    requires jdk.jdi;


    opens at.tiefenbrunner.swen2semesterprojekt to javafx.fxml;
    opens at.tiefenbrunner.swen2semesterprojekt.view to javafx.fxml;

    exports at.tiefenbrunner.swen2semesterprojekt;
    exports at.tiefenbrunner.swen2semesterprojekt.view;
    exports at.tiefenbrunner.swen2semesterprojekt.viewmodel;
    exports at.tiefenbrunner.swen2semesterprojekt.util;
    exports at.tiefenbrunner.swen2semesterprojekt.service;
    exports at.tiefenbrunner.swen2semesterprojekt.event;
}