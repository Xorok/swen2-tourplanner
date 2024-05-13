package at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentationModels;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourType;
import com.sun.jdi.InvalidTypeException;
import javafx.beans.property.*;

public class TourModel {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty from = new SimpleStringProperty();
    private StringProperty to = new SimpleStringProperty();
    private StringProperty tourType = new SimpleStringProperty();
    private DoubleProperty distanceM = new SimpleDoubleProperty();
    private LongProperty estimatedTimeMin = new SimpleLongProperty();

    public TourModel() {
    }

    public void setModel(Tour model) {
        this.name.set(model.getName());
        this.description.set(model.getDescription());
        this.from.set(model.getFrom());
        this.to.set(model.getTo());
        this.tourType.set(model.getTourType().displayLabel);
        this.distanceM.set(model.getDistanceM());
        this.estimatedTimeMin.set(model.getEstimatedTime().toMinutes());
    }

    public void updateModel(Tour model) throws InvalidTypeException {
        model.setName(this.name.get());
        model.setDescription(this.description.get());
        model.setFrom(this.from.get());
        model.setTo(this.to.get());
        model.setTourType(TourType.mapFrom(this.tourType.get()));
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

    public final StringProperty tourTypeProperty() {
        return tourType;
    }

    public final DoubleProperty distanceMProperty() {
        return distanceM;
    }

    public final LongProperty estimatedTimeMinProperty() {
        return estimatedTimeMin;
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

    public String getTourType() {
        return tourType.get();
    }

    public void setTourType(String tourType) {
        this.tourType.set(tourType);
    }

    public double getDistanceM() {
        return distanceM.get();
    }

    public void setDistanceM(double distanceM) {
        this.distanceM.set(distanceM);
    }

    public long getEstimatedTimeMin() {
        return estimatedTimeMin.get();
    }

    public void setEstimatedTimeMin(long estimatedTimeMin) {
        this.estimatedTimeMin.set(estimatedTimeMin);
    }
}
