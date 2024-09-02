package at.tiefenbrunner.swen2semesterprojekt.viewmodel.presentation;

import at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts.Point;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PointModel {
    private Point point;

    private final DoubleProperty xProperty;
    private final DoubleProperty yProperty;

    public PointModel() {
        this.point = new Point();
        this.xProperty = new SimpleDoubleProperty();
        this.yProperty = new SimpleDoubleProperty();
        setupBindings();
    }

    public PointModel(double x, double y) {
        this.point = new Point(x, y);
        this.xProperty = new SimpleDoubleProperty(x);
        this.yProperty = new SimpleDoubleProperty(y);
        setupBindings();
    }

    private void setupBindings() {
        // Bind the point's coordinates to the properties
        this.xProperty.addListener((obs, oldVal, newVal) -> point.setLocation(newVal.doubleValue(), point.getY()));
        this.yProperty.addListener((obs, oldVal, newVal) -> point.setLocation(point.getX(), newVal.doubleValue()));
    }

    public double getX() {
        return xProperty.get();
    }

    public void setX(double x) {
        this.xProperty.set(x);
    }

    public DoubleProperty xProperty() {
        return xProperty;
    }

    public double getY() {
        return yProperty.get();
    }

    public void setY(double y) {
        this.yProperty.set(y);
    }

    public DoubleProperty yProperty() {
        return yProperty;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        this.xProperty.set(point.getX());
        this.yProperty.set(point.getY());
    }
}