package at.tiefenbrunner.swen2semesterprojekt.view.controller;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.TourViewModel;
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