package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.*;

public class ViewModelFactory {
    private static ViewModelFactory instance;

    private final Publisher publisher;

    // Factories
    private final ModelFactory modelFactory;

    // View-Models
    private MainViewModel mainViewModel;
    private SearchBarViewModel searchBarViewModel;
    private TourListViewModel tourListViewModel;
    private TourViewModel tourViewModel;
    private TourDetailsViewModel tourDetailsViewModel;
    private TourLogsViewModel tourLogsViewModel;

    private ViewModelFactory(ModelFactory modelFactory) {
        publisher = new Publisher(); // TODO: Move to Factory
        this.modelFactory = modelFactory;
    }

    public static ViewModelFactory getInstance(ModelFactory modelFactory) {
        if (null == instance) {
            instance = new ViewModelFactory(modelFactory);
        }
        return instance;
    }

    public MainViewModel getMainViewModel() {
        if (mainViewModel == null)
            mainViewModel = new MainViewModel(publisher, modelFactory.getModel());
        return mainViewModel;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        if (searchBarViewModel == null)
            searchBarViewModel = new SearchBarViewModel(publisher, modelFactory.getModel());
        return searchBarViewModel;
    }

    public TourListViewModel getTourListViewModel() {
        if (tourListViewModel == null)
            tourListViewModel = new TourListViewModel(publisher, modelFactory.getModel());
        return tourListViewModel;
    }

    public TourViewModel getTourViewModel() {
        if (tourViewModel == null)
            tourViewModel = new TourViewModel(publisher, modelFactory.getModel());
        return tourViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        if (tourDetailsViewModel == null)
            tourDetailsViewModel = new TourDetailsViewModel(publisher, modelFactory.getModel());
        return tourDetailsViewModel;
    }

    public TourLogsViewModel getTourLogsViewModel() {
        if (tourLogsViewModel == null)
            tourLogsViewModel = new TourLogsViewModel(publisher, modelFactory.getModel());
        return tourLogsViewModel;
    }
}
