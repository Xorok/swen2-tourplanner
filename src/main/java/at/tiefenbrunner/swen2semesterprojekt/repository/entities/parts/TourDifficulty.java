package at.tiefenbrunner.swen2semesterprojekt.repository.entities.parts;

import com.sun.jdi.InvalidTypeException;

public enum TourDifficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD"); // TODO: Move to gui_string resource file

    private final String displayLabel;

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

    public static TourDifficulty mapFrom(int value) throws InvalidTypeException {
        if (value == 1) return EASY;
        else if (value == 2) return MEDIUM;
        else if (value == 3) return HARD;
        else throw new InvalidTypeException("No Difficulty mapping for \"" + value + "\"!");
    }

    public String toString() {
        return displayLabel;
    }

    public int toNumberGrade() {
        return switch (this) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case HARD -> 3;
        };
    }
}
