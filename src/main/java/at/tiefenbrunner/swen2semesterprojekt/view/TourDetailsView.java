package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.TourType;
import at.tiefenbrunner.swen2semesterprojekt.view.util.CoordinateStringConverter;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

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
    private Label popularity;
    @FXML
    private Label childFriendliness;
    @FXML
    private Button saveBtn;
    @FXML
    private Label errorMsg;
    @FXML
    private Button exportBtn;

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
        NumberStringConverter coordinateConverter = new CoordinateStringConverter();

        name.textProperty().bindBidirectional(viewModel.getTourModel().nameProperty());
        description.textProperty().bindBidirectional(viewModel.getTourModel().descriptionProperty());
        fromX.textProperty().bindBidirectional(viewModel.getTourModel().fromXProperty(), coordinateConverter);
        fromY.textProperty().bindBidirectional(viewModel.getTourModel().fromYProperty(), coordinateConverter);
        toX.textProperty().bindBidirectional(viewModel.getTourModel().toXProperty(), coordinateConverter);
        toY.textProperty().bindBidirectional(viewModel.getTourModel().toYProperty(), coordinateConverter);
        type.valueProperty().bindBidirectional(viewModel.getTourModel().tourTypeProperty());
        distance.textProperty().bind(viewModel.getTourModel().distanceMProperty().asString());
        time.textProperty().bind(viewModel.getTourModel().estimatedTimeMinProperty().asString());
        popularity.textProperty().bind(viewModel.getTourModel().popularityProperty().asString());
        childFriendliness.textProperty().bind(viewModel.getTourModel().childFriendlinessProperty().asString());
        this.saveBtn.disableProperty().bind(viewModel.saveDisabledProperty());
        this.exportBtn.disableProperty().bind(viewModel.exportDisabledProperty());
        errorMsg.textProperty().bind(viewModel.getTourModel().errorMessageProperty());
    }

    @FXML
    private void onSave() {
        viewModel.save();
    }

    @FXML
    private void onExport() {
        viewModel.export();
    }
}