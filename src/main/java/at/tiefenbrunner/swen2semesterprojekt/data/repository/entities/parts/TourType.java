package at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.parts;

import com.sun.jdi.InvalidTypeException;

public enum TourType {
    BIKE("BIKE"),
    HIKE("HIKE"),
    RUNNING("RUNNING"),
    VACATION("VACATION"); // TODO: Move to gui_string resource file

    private final String displayLabel;

    private TourType(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public static TourType mapFrom(String value) throws InvalidTypeException {
        value = value != null ? value.toUpperCase() : "";
        return switch (value) {
            case "BIKE" -> BIKE;
            case "HIKE" -> HIKE;
            case "RUNNING" -> RUNNING;
            case "VACATION" -> VACATION;
            default -> throw new InvalidTypeException("No Type mapping for \"" + value + "\"!");
        };
    }

    public String toString() {
        return displayLabel;
    }
}
