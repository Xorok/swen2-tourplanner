package at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourDifficulty;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import jakarta.annotation.Nullable;
import javafx.beans.property.*;

import java.time.Duration;
import java.time.Instant;

public class TourLogModel {

    private ObjectProperty<Instant> dateTime = new SimpleObjectProperty<>(Instant.now());
    private StringProperty comment = new SimpleStringProperty();
    private IntegerProperty distance = new SimpleIntegerProperty();
    private LongProperty totalTime = new SimpleLongProperty();
    private IntegerProperty rating = new SimpleIntegerProperty();
    private ObjectProperty<TourDifficulty> tourDifficulty = new SimpleObjectProperty<>(TourDifficulty.MEDIUM);

    public TourLogModel() {
    }

    public void setModel(@Nullable TourLog model) {
        this.dateTime.set(Instant.now());

        if (model == null) {
            this.comment.set("");
            this.distance.set(0);
            this.totalTime.set(0);
            this.rating.set(0);
            this.tourDifficulty.set(TourDifficulty.MEDIUM);
        } else {
            this.comment.set(model.getComment());
            this.distance.set(model.getTotalDistanceM());
            this.totalTime.set(model.getTotalTime().toMinutes());
            this.rating.set(model.getRating());
            this.tourDifficulty.set(model.getDifficulty());
        }
    }

    public void transferDataToTourLog(TourLog tourLog) {
        tourLog.setDateTime(this.dateTime.get());
        tourLog.setComment(this.comment.get());
        tourLog.setTotalDistanceM(this.distance.get());
        tourLog.setTotalTime(Duration.ofMinutes(this.totalTime.get()));
        tourLog.setRating(this.rating.get());
        tourLog.setDifficulty(this.tourDifficulty.get());
    }

    public Instant getDateTime() {
        return dateTime.get();
    }

    public ObjectProperty<Instant> dateTimeProperty() {
        return dateTime;
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public int getDistance() {
        return distance.get();
    }

    public IntegerProperty distanceProperty() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance.set(distance);
    }

    public long getTotalTime() {
        return totalTime.get();
    }

    public LongProperty totalTimeProperty() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime.set(totalTime);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public TourDifficulty getTourDifficulty() {
        return tourDifficulty.get();
    }

    public ObjectProperty<TourDifficulty> tourDifficultyProperty() {
        return tourDifficulty;
    }

    public void setTourDifficulty(TourDifficulty tourDifficulty) {
        this.tourDifficulty.set(tourDifficulty);
    }
}
