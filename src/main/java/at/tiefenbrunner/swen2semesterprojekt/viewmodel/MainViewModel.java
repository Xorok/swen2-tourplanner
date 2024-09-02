package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MainViewModel {
    private final Publisher publisher;
    private final ConfigService configService;

    private BooleanProperty darkTheme;

    public MainViewModel(Publisher publisher, ConfigService configService) {
        this.publisher = publisher;
        this.configService = configService;

        setupTheme();
        setupBindings();
    }

    private void setupTheme() {
        darkTheme = new SimpleBooleanProperty(
                Boolean.parseBoolean(configService.getConfigValue("app.dark-theme"))
        );
    }

    private void setupBindings() {
        // if theme switch button is pressed, propagate event
        this.darkTheme.addListener(
                observable -> propagateAndSaveThemeChange()
        );
    }

    private void propagateAndSaveThemeChange() {
        publisher.publish(Event.SWITCH_THEME, "");
        configService.setConfigValue("app.dark-theme", this.darkTheme.get() ? "true" : "false");
    }

    public BooleanProperty darkThemeProperty() {
        return darkTheme;
    }
}
