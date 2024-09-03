package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.service.TourService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MainViewModel {
    private final Publisher publisher;
    private final ConfigService configService;
    private final TourService tourService;

    private BooleanProperty darkTheme;

    public MainViewModel(Publisher publisher, ConfigService configService, TourService tourService) {
        this.publisher = publisher;
        this.configService = configService;
        this.tourService = tourService;

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

    public void exportPdf() {
        tourService.generateSummaryReport();
    }

    public BooleanProperty darkThemeProperty() {
        return darkTheme;
    }
}
