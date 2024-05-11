package at.tiefenbrunner.swen2semesterprojekt;

import at.tiefenbrunner.swen2semesterprojekt.core.ViewHandler;
import at.tiefenbrunner.swen2semesterprojekt.factories.ModelFactory;
import at.tiefenbrunner.swen2semesterprojekt.factories.ViewFactory;
import at.tiefenbrunner.swen2semesterprojekt.factories.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ModelFactory mf = ModelFactory.getInstance();
        ViewModelFactory vmf = ViewModelFactory.getInstance(mf);
        ViewFactory vf = ViewFactory.getInstance(vmf);
        ViewHandler vh = ViewHandler.getInstance(vf);
        vh.start(stage);
    }
}