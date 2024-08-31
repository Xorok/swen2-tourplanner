package at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Point;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;
import com.sun.jdi.InvalidTypeException;
import jakarta.annotation.Nullable;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.time.Duration;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_ASSETS_SUBPATH;

public class TourModel {

    private final Image mapImgPlaceholder = new Image(getClass().getResourceAsStream(RES_ASSETS_SUBPATH + "map-placeholder.jpg"));

    private ObjectProperty<Image> mapImg = new SimpleObjectProperty<>(mapImgPlaceholder);
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private PointModel from = new PointModel();
    private PointModel to = new PointModel();
    private ObjectProperty<TourType> tourType = new SimpleObjectProperty<>(TourType.BIKE);
    private IntegerProperty distanceM = new SimpleIntegerProperty();
    private LongProperty estimatedTimeMin = new SimpleLongProperty();

    public TourModel() {
    }

    public void setModel(@Nullable Tour model) {
        if (model == null) {
            this.name.set("");
            this.description.set("");
            this.from.setPoint(new Point());
            this.to.setPoint(new Point());
            this.tourType.set(TourType.BIKE);
            this.distanceM.set(0);
            this.estimatedTimeMin.set(0);
            this.mapImg.set(mapImgPlaceholder);
        } else {
            this.name.set(model.getName());
            this.description.set(model.getDescription());
            this.from.setPoint(model.getFrom());
            this.to.setPoint(model.getTo());
            this.tourType.set(model.getTourType());
            this.distanceM.set(model.getDistanceM());
            this.estimatedTimeMin.set(model.getEstimatedTime().toMinutes());
            this.mapImg.set(new Image(model.getRouteMapImg(), true));
        }
    }

    public void transferDataToTour(Tour tour) throws InvalidTypeException {
        tour.setName(this.name.get());
        tour.setDescription(this.description.get());
        tour.setFrom(this.from.getPoint());
        tour.setTo(this.to.getPoint());
        tour.setTourType(this.tourType.get());
        tour.setDistanceM(this.distanceM.get());
        tour.setEstimatedTime(Duration.ofMinutes(this.estimatedTimeMin.get()));
        tour.setRouteMapImg(this.mapImg.get().getUrl());
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final StringProperty descriptionProperty() {
        return description;
    }

    public final DoubleProperty fromXProperty() {
        return from.xProperty();
    }

    public final DoubleProperty fromYProperty() {
        return from.yProperty();
    }

    public final DoubleProperty toXProperty() {
        return to.xProperty();
    }

    public final DoubleProperty toYProperty() {
        return to.yProperty();
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

    public Point getFrom() {
        return from.getPoint();
    }

    public void setFrom(Point from) {
        this.from.setPoint(from);
    }

    public Point getTo() {
        return to.getPoint();
    }

    public void setTo(Point to) {
        this.to.setPoint(to);
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
