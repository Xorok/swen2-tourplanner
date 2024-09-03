package at.tiefenbrunner.swen2semesterprojekt;

import at.tiefenbrunner.swen2semesterprojekt.data.DataFactory;
import at.tiefenbrunner.swen2semesterprojekt.data.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import at.tiefenbrunner.swen2semesterprojekt.view.ViewFactory;
import at.tiefenbrunner.swen2semesterprojekt.view.core.ViewHandler;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ConfigService configService = new ConfigService(Constants.CONFIG_FILE_PATH);

        DataFactory mf = DataFactory.getInstance(configService);
        ViewModelFactory vmf = ViewModelFactory.getInstance(mf);
        ViewFactory vf = ViewFactory.getInstance(vmf);
        ViewHandler vh = ViewHandler.getInstance(vf);
        vh.start(stage);
    }
}