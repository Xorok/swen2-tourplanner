package at.tiefenbrunner.swen2semesterprojekt.util;

public class Constants {
    public static final String CONFIG_FILE_PATH = "config.properties";
    public static final String RES_PATH = "/at/tiefenbrunner/swen2semesterprojekt/";
    public static final String RES_ASSETS_PATH = RES_PATH + "assets/";
    public static final String RES_WEB_PATH = RES_PATH + "web/";
    public static final String RES_STYLE_PATH = RES_PATH + "styles/";
    public static final String RES_BASENAME_GUI_STRINGS = "at.tiefenbrunner.swen2semesterprojekt.gui_strings"; // TODO: Use vars for UI
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String COORDINATE_FORMAT = "##0.000000";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:131.0) Gecko/20100101 Firefox/131.0";
    public static final String MAP_IMAGES_PATH = "maps/";
    public static final String REPORTS_PATH = "reports/";

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
