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

    private final SearchBarViewModel viewModel;

    public SearchBarView(SearchBarViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchField.textProperty()
                .bindBidirectional(viewModel.searchTextProperty());
    }

    @FXML
    protected void onSearch() {
        this.viewModel.search();
    }
}