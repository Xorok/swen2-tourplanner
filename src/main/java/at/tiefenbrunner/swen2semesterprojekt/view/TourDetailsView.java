package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TourDetailsView implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private TextField fromX;
    @FXML
    private TextField fromY;
    @FXML
    private TextField toX;
    @FXML
    private TextField toY;
    @FXML
    private ComboBox<TourType> type;
    @FXML
    private Label distance;
    @FXML
    private Label time;
    @FXML
    private Button saveBtn;

    private final TourDetailsViewModel viewModel;

    public TourDetailsView(TourDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUiComponents();
        setupBindings();
    }

    private void setupUiComponents() {
        type.getItems().setAll(TourType.values());
    }

    private void setupBindings() {
        name.textProperty().bindBidirectional(viewModel.getTourModel().nameProperty());
        description.textProperty().bindBidirectional(viewModel.getTourModel().descriptionProperty());
        fromX.textProperty().bindBidirectional(viewModel.getTourModel().fromXProperty(), viewModel.getCoordinateFormat());
        fromY.textProperty().bindBidirectional(viewModel.getTourModel().fromYProperty(), viewModel.getCoordinateFormat());
        toX.textProperty().bindBidirectional(viewModel.getTourModel().toXProperty(), viewModel.getCoordinateFormat());
        toY.textProperty().bindBidirectional(viewModel.getTourModel().toYProperty(), viewModel.getCoordinateFormat());
        type.valueProperty().bindBidirectional(viewModel.getTourModel().tourTypeProperty());
        distance.textProperty().bind(viewModel.getTourModel().distanceMProperty().asString());
        time.textProperty().bind(viewModel.getTourModel().estimatedTimeMinProperty().asString());
        this.saveBtn.disableProperty().bind(viewModel.saveDisabledProperty());
    }

    @FXML
    private void onSave() {
        viewModel.save();
    }
}