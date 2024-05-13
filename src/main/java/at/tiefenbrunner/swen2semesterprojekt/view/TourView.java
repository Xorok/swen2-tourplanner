package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TourView implements Initializable {
    private final TourViewModel viewModel;

    public TourView(TourViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}