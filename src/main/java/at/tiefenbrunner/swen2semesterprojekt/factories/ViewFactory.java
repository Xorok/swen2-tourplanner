package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.view.*;

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

    public Object create(Class<?> viewClass) {
        if (viewClass == MainView.class) {
            return new MainView(viewModelFactory.getMainViewModel());
        }
        if (viewClass == SearchBarView.class) {
            return new SearchBarView(viewModelFactory.getSearchBarViewModel());
        }
        if (viewClass == TourListView.class) {
            return new TourListView(viewModelFactory.getTourListViewModel());
        }
        if (viewClass == TourView.class) {
            return new TourView(viewModelFactory.getTourViewModel());
        }
        if (viewClass == TourDetailsView.class) {
            return new TourDetailsView(viewModelFactory.getTourDetailsViewModel());
        }
        if (viewClass == TourLogsView.class) {
            return new TourLogsView(viewModelFactory.getTourLogsViewModel());
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
