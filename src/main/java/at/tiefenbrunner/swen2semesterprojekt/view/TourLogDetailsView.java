package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.view.util.ThemedView;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourLogDetailsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TourLogDetailsView extends ThemedView implements Initializable {
    @FXML
    private VBox rootView;
    @FXML
    private Label dateTime;
    @FXML
    private TextArea comment;
    @FXML
    private TextField distance;
    @FXML
    private TextField totalTime;
    @FXML
    private TextField rating;
    @FXML
    private ComboBox<TourDifficulty> tourDifficulty;
    @FXML
    private Button saveBtn;

    private final TourLogDetailsViewModel viewModel;

    public TourLogDetailsView(TourLogDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTheme();
        setupUiComponents();
        setupBindings();
    }

    private void setupTheme() {
        if (viewModel.darkThemeProperty().get()) {
            setTheme(rootView, true);
        }
    }

    private void setupUiComponents() {
        tourDifficulty.getItems().setAll(TourDifficulty.values());
    }

    private void setupBindings() {
        dateTime.textProperty().bind(viewModel.getTourLogModel().dateTimeProperty());
        comment.textProperty().bindBidirectional(viewModel.getTourLogModel().commentProperty());
        distance.textProperty().bindBidirectional(viewModel.getTourLogModel().distanceProperty(), new NumberStringConverter());
        totalTime.textProperty().bindBidirectional(viewModel.getTourLogModel().totalTimeProperty(), new NumberStringConverter());
        rating.textProperty().bindBidirectional(viewModel.getTourLogModel().ratingProperty(), new NumberStringConverter());
        tourDifficulty.valueProperty().bindBidirectional(viewModel.getTourLogModel().tourDifficultyProperty());
        this.saveBtn.disableProperty().bind(viewModel.saveDisabledProperty());
        viewModel.darkThemeProperty().addListener((observable, oldValue, newValue) -> setTheme(rootView, newValue));
    }


    @FXML
    private void onSave() {
        viewModel.save();
        Stage stage = (Stage) rootView.getScene().getWindow();
        stage.close();
    }
}