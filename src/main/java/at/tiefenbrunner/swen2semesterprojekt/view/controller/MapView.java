package at.tiefenbrunner.swen2semesterprojekt.view.controller;

import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.MapViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class MapView implements Initializable {

    @FXML
    private WebView webView;
    private WebEngine webEngine;

    private final MapViewModel viewModel;

    public MapView(MapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupWebView();
        setupBindings();
    }

    private void setupWebView() {
        webEngine = webView.getEngine();
        webEngine.setOnError(log::error);
        webEngine.load(getClass().getResource(Constants.RES_WEB_PATH + "map.html").toExternalForm());
    }

    private void setupBindings() {
        // Bind the script execution
        viewModel.scriptToExecuteProperty().addListener((obs, oldScript, newScript) -> {
            if (newScript != null && !newScript.isEmpty()) {
                webEngine.executeScript(newScript);
            }
        });
    }
}
