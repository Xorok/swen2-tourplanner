package at.tiefenbrunner.swen2semesterprojekt.view;

import at.tiefenbrunner.swen2semesterprojekt.view.controller.*;
import at.tiefenbrunner.swen2semesterprojekt.view.core.ViewHandler;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.ViewModelFactory;

public class ViewFactory {

    private static ViewFactory instance;

    // Factories
    private final ViewModelFactory viewModelFactory;

    private ViewFactory(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }

    public static ViewFactory getInstance(ViewModelFactory viewModelFactory) {
        if (null == instance) {
            instance = new ViewFactory(viewModelFactory);
        }
        return instance;
    }

    public Object create(Class<?> viewClass, ViewHandler viewHandler) {
        if (viewClass == MainView.class) {
            return new MainView(viewModelFactory.getMainViewModel());
        }
        if (viewClass == SearchBarView.class) {
            return new SearchBarView(viewModelFactory.getSearchBarViewModel());
        }
        if (viewClass == ToursListView.class) {
            return new ToursListView(viewModelFactory.getTourListViewModel());
        }
        if (viewClass == TourView.class) {
            return new TourView(viewModelFactory.getTourViewModel());
        }
        if (viewClass == TourDetailsView.class) {
            return new TourDetailsView(viewModelFactory.getTourDetailsViewModel());
        }
        if (viewClass == MapView.class) {
            return new MapView(viewModelFactory.getMapViewModel());
        }
        if (viewClass == TourLogsView.class) {
            return new TourLogsView(viewModelFactory.getTourLogsViewModel(), viewHandler);
        }
        if (viewClass == TourLogDetailsView.class) {
            return new TourLogDetailsView(viewModelFactory.getTourLogDetailsViewModel());
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
