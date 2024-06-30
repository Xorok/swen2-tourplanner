package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.core.ViewHandler;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class TourLogsView implements Initializable {
    private final TourLogsViewModel viewModel;
    private final ViewHandler viewHandler;

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

    public TourLogsView(TourLogsViewModel viewModel, ViewHandler viewHandler) {
        this.viewModel = viewModel;
        this.viewHandler = viewHandler;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUiComponents();
        setupBindings();
    }

    private void setupUiComponents() {
        logsTable.setRowFactory(tv -> {
            TableRow<TourLog> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    if (row.isEmpty()) {
                        try {
                            viewHandler.openWindow(Constants.Windows.CREATE_LOG, null);
                            viewModel.createTourLog();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                            // TODO: Handle exception
                        }
                    } else {
                        try {
                            viewHandler.openWindow(Constants.Windows.EDIT_LOG, null);
                            TourLog clickedLog = row.getItem();
                            viewModel.editTourLog(clickedLog);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                            // TODO: Handle exception
                        }
                    }
                }
            });
            return row;
        });
    }

    private void setupBindings() {
        this.logsTable.setItems(viewModel.getLogs()); // Binding through Observable List
    }
}