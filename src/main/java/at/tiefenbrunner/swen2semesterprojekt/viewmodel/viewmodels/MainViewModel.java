package at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels;

import at.tiefenbrunner.swen2semesterprojekt.data.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
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
        publisher.publish(Event.DARK_THEME, this.darkTheme.get() ? "true" : "false");
        configService.setConfigValue("app.dark-theme", this.darkTheme.get() ? "true" : "false");
    }

    public void exportPdf() {
        tourService.generateSummaryReport();
    }

    public void exportJson() {
        tourService.generateJsonBackup();
    }

    public void importJson() {
        tourService.importJsonBackup();
        publisher.publish(Event.SEARCH_TERM_SEARCHED, "");
    }

    public BooleanProperty darkThemeProperty() {
        return darkTheme;
    }
}
