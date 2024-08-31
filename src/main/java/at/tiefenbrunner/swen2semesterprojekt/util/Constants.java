package at.tiefenbrunner.swen2semesterprojekt.util;

public class Constants {
    public static final String CONFIG_FILE_PATH = "config.properties";
    public static final String RES_SUBPATH = "/at/tiefenbrunner/swen2semesterprojekt/";
    public static final String RES_ASSETS_SUBPATH = RES_SUBPATH + "assets/";
    public static final String RES_WEB_SUBPATH = RES_SUBPATH + "web/";
    public static final String RES_BASENAME_GUI_STRINGS = "at.tiefenbrunner.swen2semesterprojekt.gui_strings";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";

    public enum Windows {
        MAIN("views/main-view.fxml", "Tour Planner - Main View"), // TODO: Move to gui_string resource file
        EDIT_LOG("views/tour-log-details-view.fxml", "Tour Planner - Edit Log"), // TODO: Move to gui_string resource file
        CREATE_LOG("views/tour-log-details-view.fxml", "Tour Planner - Create Log"); // TODO: Move to gui_string resource file

        public final String fxml;
        public final String windowTitle;

        private Windows(String fxml, String windowTitle) {
            this.fxml = fxml;
            this.windowTitle = windowTitle;
        }
    }
}
