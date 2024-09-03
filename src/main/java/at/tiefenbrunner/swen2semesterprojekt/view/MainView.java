package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.view.util.ThemedView;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainView extends ThemedView implements Initializable {
    @FXML
    private BorderPane borderPane;

    private final MainViewModel viewModel;

    public MainView(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTheme();
    }

    private void setupTheme() {
        if (viewModel.darkThemeProperty().get()) {
            setTheme(borderPane, true);
        }
    }

    @FXML
    public void onSwitchTheme() {
        boolean newThemeState = !viewModel.darkThemeProperty().get();

        // Notify ViewModel
        viewModel.darkThemeProperty().set(newThemeState);

        setTheme(borderPane, newThemeState);
    }

    @FXML
    public void onExportPdf() {
        viewModel.exportPdf();
    }

    @FXML
    public void onExportJson() {
        viewModel.exportJson();
    }

    @FXML
    public void onImportJson() {
        viewModel.importJson();
    }
}