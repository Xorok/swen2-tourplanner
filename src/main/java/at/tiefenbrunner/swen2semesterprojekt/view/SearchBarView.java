package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.SearchBarViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarView implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button showAllButton;

    private final SearchBarViewModel viewModel;

    public SearchBarView(SearchBarViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupBindings();
    }

    private void setupBindings() {
        this.searchField.textProperty().bindBidirectional(viewModel.searchTextProperty());
        this.searchButton.disableProperty().bind(viewModel.searchDisabledProperty());
    }

    @FXML
    protected void onSearch() {
        this.viewModel.search();
    }

    @FXML
    protected void onShowAll() {
        this.viewModel.showAll();
    }
}