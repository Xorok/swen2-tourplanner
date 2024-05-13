package at.tiefenbrunner.swen2semesterprojekt.repository.entities;

import com.sun.jdi.InvalidTypeException;

public enum TourDifficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD"); // TODO: Move to gui_string resource file

    public final String displayLabel;

    private TourDifficulty(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public static TourDifficulty mapFrom(String value) throws InvalidTypeException {
        value = value != null ? value.toUpperCase() : "";
        return switch (value) {
            case "EASY" -> EASY;
            case "MEDIUM" -> MEDIUM;
            case "HARD" -> HARD;
            default -> throw new InvalidTypeException("No Difficulty mapping for \"" + value + "\"!");
        };
    }

    public String toString() {
        return displayLabel;
    }
}
