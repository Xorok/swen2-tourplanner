package at.tiefenbrunner.swen2semesterprojekt.view.controller;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.ToursListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ToursListView implements Initializable {

    private final ToursListViewModel viewModel;

    @FXML
    private ListView<String> toursList;

    public ToursListView(ToursListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.toursList.setItems(viewModel.getTourNames()); // Binding through Observable List
        this.viewModel.selectedTourIndexProperty().bind(toursList.getSelectionModel().selectedIndexProperty());
    }

    @FXML
    private void onAddNew() {
        viewModel.addNew();
        toursList.getSelectionModel().clearSelection();
    }

    @FXML
    private void onDelete() {
        viewModel.delete();
    }
}
