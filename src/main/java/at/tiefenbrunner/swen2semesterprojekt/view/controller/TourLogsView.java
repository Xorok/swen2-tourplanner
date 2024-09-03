package at.tiefenbrunner.swen2semesterprojekt.view.controller;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import at.tiefenbrunner.swen2semesterprojekt.util.DurationFormat;
import at.tiefenbrunner.swen2semesterprojekt.view.core.ViewHandler;
import at.tiefenbrunner.swen2semesterprojekt.view.util.ColumnFormatter;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TourLogsView implements Initializable {
    private final TourLogsViewModel viewModel;
    private final ViewHandler viewHandler;
    private final Format dateTimeFormat = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT).withZone(ZoneId.systemDefault()).toFormat();
    private final Format durationFormat = new DurationFormat();

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
    @FXML
    private ButtonBar buttonBar;

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
                        addNewLog();
                    } else {
                        editLog(row);
                    }
                }
            });
            return row;
        });

        dateTime.setCellFactory(new ColumnFormatter<>(dateTimeFormat));
        totalTime.setCellFactory(new ColumnFormatter<>(durationFormat));
    }

    private void setupBindings() {
        this.logsTable.setItems(viewModel.getLogs()); // Binding through Observable List
        this.buttonBar.disableProperty().bind(viewModel.crudDisabledProperty());
    }

    @FXML
    private void onAddNew() {
        addNewLog();
    }

    @FXML
    private void onDelete() {
        deleteLog();
    }

    private void addNewLog() {
        try {
            viewHandler.openWindow(Constants.Windows.CREATE_LOG, null);
            viewModel.createTourLog();
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: Handle exception
        }
    }

    private void editLog(TableRow<TourLog> row) {
        try {
            viewHandler.openWindow(Constants.Windows.EDIT_LOG, null);
            TourLog clickedLog = row.getItem();
            viewModel.editTourLog(clickedLog);
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: Handle exception
        }
    }

    private void deleteLog() {
        TourLog selectedLog = logsTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            viewModel.deleteTourLog(selectedLog);
        }
    }
}