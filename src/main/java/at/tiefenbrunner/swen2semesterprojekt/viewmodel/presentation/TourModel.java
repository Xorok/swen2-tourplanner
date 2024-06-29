package at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;
import com.sun.jdi.InvalidTypeException;
import jakarta.annotation.Nullable;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.time.Duration;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_SUBPATH;

public class TourModel {

    private final Image mapImgPlaceholder = new Image(RES_SUBPATH + "map-placeholder/map-placeholder.jpg", true);

    private ObjectProperty<Image> mapImg = new SimpleObjectProperty<>(mapImgPlaceholder);
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty from = new SimpleStringProperty();
    private StringProperty to = new SimpleStringProperty();
    private ObjectProperty<TourType> tourType = new SimpleObjectProperty<>(new TourType("TEST")); // TODO
    private IntegerProperty distanceM = new SimpleIntegerProperty();
    private LongProperty estimatedTimeMin = new SimpleLongProperty();

    public TourModel() {
    }

    public void setModel(@Nullable Tour model) {
        if (model == null) {
            this.name.set("");
            this.description.set("");
            this.from.set("");
            this.to.set("");
            this.tourType.set(new TourType("TEST")); // TODO
            this.distanceM.set(0);
            this.estimatedTimeMin.set(0);
            this.mapImg.set(mapImgPlaceholder);
        } else {
            this.name.set(model.getName());
            this.description.set(model.getDescription());
            this.from.set(model.getFrom());
            this.to.set(model.getTo());
            this.tourType.set(model.getTourType());
            this.distanceM.set(model.getDistanceM());
            this.estimatedTimeMin.set(model.getEstimatedTime().toMinutes());
            this.mapImg.set(new Image(model.getRouteMapImg(), true));
        }
    }

    public void updateModel(Tour model) throws InvalidTypeException {
        model.setName(this.name.get());
        model.setDescription(this.description.get());
        model.setFrom(this.from.get());
        model.setTo(this.to.get());
        model.setTourType(this.tourType.get());
        model.setDistanceM(this.distanceM.get());
        model.setEstimatedTime(Duration.ofMinutes(this.estimatedTimeMin.get()));
        model.setRouteMapImg(this.mapImg.get().getUrl());
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final StringProperty descriptionProperty() {
        return description;
    }

    public final StringProperty fromProperty() {
        return from;
    }

    public final StringProperty toProperty() {
        return to;
    }

    public final ObjectProperty<TourType> tourTypeProperty() {
        return tourType;
    }

    public final IntegerProperty distanceMProperty() {
        return distanceM;
    }

    public final LongProperty estimatedTimeMinProperty() {
        return estimatedTimeMin;
    }

    public final ObjectProperty<Image> mapImgProperty() {
        return mapImg;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getFrom() {
        return from.get();
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getTo() {
        return to.get();
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public TourType getTourType() {
        return tourType.get();
    }

    public void setTourType(TourType tourType) {
        this.tourType.set(tourType);
    }

    public double getDistanceM() {
        return distanceM.get();
    }

    public void setDistanceM(int distanceM) {
        this.distanceM.set(distanceM);
    }

    public long getEstimatedTimeMin() {
        return estimatedTimeMin.get();
    }

    public void setEstimatedTimeMin(long estimatedTimeMin) {
        this.estimatedTimeMin.set(estimatedTimeMin);
    }

    public Image getMapImg() {
        return mapImg.get();
    }

    public void setMapImg(Image mapImg) {
        this.mapImg.set(mapImg);
    }
}
