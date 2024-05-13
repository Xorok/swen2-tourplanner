package at.tiefenbrunner.swen2semesterprojekt.util;

public class Constants {
    public static final String RES_SUBPATH = "/at/tiefenbrunner/swen2semesterprojekt/";
    public static final String RES_BASENAME_GUI_STRINGS = "at.tiefenbrunner.swen2semesterprojekt.gui_strings";

    public enum Windows {
        MAIN("views/main-view.fxml", "Tour Planner - Main View"); // TODO: Move to gui_string resource file

        public final String fxml;
        public final String windowTitle;

        private Windows(String fxml, String windowTitle) {
            this.fxml = fxml;
            this.windowTitle = windowTitle;
        }
    }
}
