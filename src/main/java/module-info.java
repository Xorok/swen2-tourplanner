module at.tiefenbrunner.swen2semesterprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires jakarta.annotation;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires jdk.jdi;
    requires jakarta.persistence;
    requires javafx.web;
    requires org.json;
    requires layout;
    requires kernel;
    requires org.slf4j;
    requires io;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens at.tiefenbrunner.swen2semesterprojekt to javafx.fxml;
    opens at.tiefenbrunner.swen2semesterprojekt.view to javafx.fxml;
    opens at.tiefenbrunner.swen2semesterprojekt.util to javafx.fxml;
    opens at.tiefenbrunner.swen2semesterprojekt.repository.entities;

    exports at.tiefenbrunner.swen2semesterprojekt;
    opens at.tiefenbrunner.swen2semesterprojekt.view.util to javafx.fxml;
    opens at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts;
}