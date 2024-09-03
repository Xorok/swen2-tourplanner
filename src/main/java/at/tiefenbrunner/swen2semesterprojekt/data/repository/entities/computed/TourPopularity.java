package at.tiefenbrunner.swen2semesterprojekt.data.repository.entities.computed;

import com.sun.jdi.InvalidTypeException;

public enum TourPopularity {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW"); // TODO: Move to gui_string resource file

    private final String displayLabel;

    private TourPopularity(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public static TourPopularity mapFrom(String value) throws InvalidTypeException {
        value = value != null ? value.toUpperCase() : "";
        return switch (value) {
            case "HIGH" -> HIGH;
            case "MEDIUM" -> MEDIUM;
            case "LOW" -> LOW;
            default -> throw new InvalidTypeException("No Popularity mapping for \"" + value + "\"!");
        };
    }

    public String toString() {
        return displayLabel;
    }
}
