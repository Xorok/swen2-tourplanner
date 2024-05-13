package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourListViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListView implements Initializable {

    private final TourListViewModel viewModel;

    @FXML
    private ListView<String> tourList;

    public TourListView(TourListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tourList.setItems(viewModel.getTourNames()); // Binding through Observable List
        this.viewModel.selectedTourIndexProperty().bind(tourList.getSelectionModel().selectedIndexProperty());
    }

    @FXML
    private void onAddNew() {
        viewModel.addNew();
    }

    @FXML
    private void onDelete() {
        viewModel.delete();
    }
}
