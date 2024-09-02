package at.tiefenbrunner.swen2semesterprojekt.view.util;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

import java.util.function.Predicate;

import static at.tiefenbrunner.swen2semesterprojekt.util.Constants.RES_STYLE_PATH;

public class ThemedView {
    public void setTheme(Parent rootContainer, boolean darkTheme) {
        String darkThemeCssFile = "dark_theme.css";
        Predicate<String> hasDarkTheme = (String stylesheet) -> stylesheet.endsWith(darkThemeCssFile);

        ObservableList<String> stylesheets = rootContainer.getStylesheets();
        if (darkTheme) {
            String cssTheme = getClass().getResource(RES_STYLE_PATH + darkThemeCssFile).toExternalForm();
            if (stylesheets.stream().noneMatch(hasDarkTheme)) {
                stylesheets.add(cssTheme);
            }
        } else {
            stylesheets.removeIf(hasDarkTheme);
        }
    }
}
