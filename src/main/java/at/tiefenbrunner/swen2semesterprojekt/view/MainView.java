package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainView {
    @FXML
    private TableView table;
    @FXML
    private ListView tourList;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;


    private MainViewModel viewModel;

    public MainView(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    protected void onSearch() {

    }
}