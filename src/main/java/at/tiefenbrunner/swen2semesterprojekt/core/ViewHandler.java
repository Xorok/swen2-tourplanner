package at.tiefenbrunner.swen2semesterprojekt.core;

import at.tiefenbrunner.swen2semesterprojekt.factories.ViewFactory;
import jakarta.annotation.Nullable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.*;

public class ViewHandler {

    private static ViewHandler instance;

    // Factories
    private final ViewFactory viewFactory;

    private ViewHandler(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    public static ViewHandler getInstance(ViewFactory viewFactory) {
        if (instance == null) {
            instance = new ViewHandler(viewFactory);
        }
        return instance;
    }

    public void start(Stage mainStage) throws IOException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        mainStage.setWidth(width * 0.7);
        mainStage.setHeight(height * 0.7);
        openWindow(Windows.MAIN, mainStage);
    }

    public void openWindow(Windows viewToOpen, @Nullable Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                ViewHandler.class.getResource(RES_PATH + viewToOpen.fxml),
                ResourceBundle.getBundle(RES_BASENAME_GUI_STRINGS, Locale.ENGLISH), // TODO: Make language configurable
                new JavaFXBuilderFactory(),
                (Class<?> viewClass) -> viewFactory.create(viewClass, this)
        );
        Parent view = loader.load();
        Scene scene = new Scene(view);
        Stage window = stage != null ? stage : new Stage();
        window.setTitle(viewToOpen.windowTitle);
        window.getIcons().add(new Image(getClass().getResourceAsStream(RES_ASSETS_PATH + "app-icon.png")));
        window.setScene(scene);
        window.show();
    }
}
