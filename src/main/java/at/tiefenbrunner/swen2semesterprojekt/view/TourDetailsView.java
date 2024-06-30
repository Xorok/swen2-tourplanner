package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class TourDetailsView implements Initializable {
    @FXML
    private ImageView mapImage;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
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
        mapImage.imageProperty().bind(viewModel.getTourModel().mapImgProperty());
        name.textProperty().bindBidirectional(viewModel.getTourModel().nameProperty());
        description.textProperty().bindBidirectional(viewModel.getTourModel().descriptionProperty());
        from.textProperty().bindBidirectional(viewModel.getTourModel().fromProperty());
        to.textProperty().bindBidirectional(viewModel.getTourModel().toProperty());
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