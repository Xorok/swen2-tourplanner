package at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation;

import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.TourPoint;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.computed.TourChildFriendliness;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.computed.TourPopularity;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.Point;
import at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts.TourType;
import jakarta.annotation.Nullable;
import javafx.beans.property.*;

import java.util.List;

public class TourModel {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final PointModel from = new PointModel();
    private final PointModel to = new PointModel();
    private final ObjectProperty<TourType> tourType = new SimpleObjectProperty<>(TourType.BIKE);
    private final IntegerProperty distanceM = new SimpleIntegerProperty();
    private final LongProperty estimatedTimeMin = new SimpleLongProperty();
    private final ObjectProperty<TourPopularity> popularity = new SimpleObjectProperty<>(TourPopularity.LOW);
    private final ObjectProperty<TourChildFriendliness> childFriendliness = new SimpleObjectProperty<>(TourChildFriendliness.MEDIUM);
    private final StringProperty errMsg = new SimpleStringProperty();

    public TourModel() {
    }

    public void setModel(@Nullable Tour model) {
        errMsg.set("");

        if (model == null) {
            this.name.set("");
            this.description.set("");
            this.from.setPoint(new Point());
            this.to.setPoint(new Point());
            this.tourType.set(TourType.BIKE);
            this.distanceM.set(0);
            this.estimatedTimeMin.set(0);
            this.popularity.set(TourPopularity.LOW);
            this.childFriendliness.set(TourChildFriendliness.MEDIUM);
        } else {
            List<TourPoint> points = model.getTourPoints();
            this.name.set(model.getName());
            this.description.set(model.getDescription());
            if (points != null && !points.isEmpty()) {
                this.from.setPoint(points.getFirst().getPoint());
                this.to.setPoint(points.getLast().getPoint());
            }
            this.tourType.set(model.getTourType());
            this.distanceM.set(model.getDistanceM());
            this.estimatedTimeMin.set(model.getEstimatedTime().toMinutes());
            this.popularity.set(model.getPopularity());
            this.childFriendliness.set(model.getChildFriendliness());
        }
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

    public final ObjectProperty<TourPopularity> popularityProperty() {
        return popularity;
    }

    public final ObjectProperty<TourChildFriendliness> childFriendlinessProperty() {
        return childFriendliness;
    }

    public final StringProperty errorMessageProperty() {
        return errMsg;
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

    public int getDistanceM() {
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

    public String getErrorMessage() {
        return errMsg.get();
    }

    public void setErrorMessage(String errorMessage) {
        this.errMsg.set(errorMessage);
    }
}
