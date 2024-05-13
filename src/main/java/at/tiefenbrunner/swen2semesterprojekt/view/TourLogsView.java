package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private TableColumn<Instant, String> totalTime;

    public TourLogsView(TourLogsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUiComponents();

        this.logsTable.setItems(viewModel.getLogs()); // Binding through Observable List
    }

    private void setupUiComponents() {
        // TODO
    }
}