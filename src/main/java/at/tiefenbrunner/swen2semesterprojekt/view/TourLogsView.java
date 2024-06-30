package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class TourLogsView implements Initializable {
    private final TourLogsViewModel viewModel;

    @FXML
    private TableView<TourLog> logsTable;
    @FXML
    private TableColumn<Instant, String> dateTime;
    @FXML
    private TableColumn<String, String> comment;
    @FXML
    private TableColumn<Integer, String> distance;
    @FXML
    private TableColumn<Instant, String> totalTime;
    @FXML
    private TableColumn<Integer, String> rating;
    @FXML
    private TableColumn<TourDifficulty, TourDifficulty> tourDifficulty;

    public TourLogsView(TourLogsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUiComponents();
        setupBindings();
    }

    private void setupBindings() {
        this.logsTable.setItems(viewModel.getLogs()); // Binding through Observable List

    }

    private void setupUiComponents() {
        comment.setCellFactory(column -> new TextFieldTableCell<>());
        distance.setCellFactory(column -> new TextFieldTableCell<>());
        totalTime.setCellFactory(column -> new TextFieldTableCell<>());
        rating.setCellFactory(column -> new TextFieldTableCell<>());
        tourDifficulty.setCellFactory(column -> new ComboBoxTableCell<>(TourDifficulty.values()));
    }
}