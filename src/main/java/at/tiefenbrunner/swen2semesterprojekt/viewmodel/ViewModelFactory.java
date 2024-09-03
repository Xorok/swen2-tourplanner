package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.data.DataFactory;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.*;

public class ViewModelFactory {
    private static ViewModelFactory instance;

    private final Publisher publisher;

    // Factories
    private final DataFactory dataFactory;

    // View-Models
    private MainViewModel mainViewModel;
    private SearchBarViewModel searchBarViewModel;
    private ToursListViewModel toursListViewModel;
    private TourViewModel tourViewModel;
    private TourDetailsViewModel tourDetailsViewModel;
    private MapViewModel mapViewModel;
    private TourLogsViewModel tourLogsViewModel;
    private TourLogDetailsViewModel tourLogDetailsViewModel;

    private ViewModelFactory(DataFactory dataFactory) {
        publisher = new Publisher(); // TODO: Move to Factory
        this.dataFactory = dataFactory;
    }

    public static ViewModelFactory getInstance(DataFactory dataFactory) {
        if (null == instance) {
            instance = new ViewModelFactory(dataFactory);
        }
        return instance;
    }

    public MainViewModel getMainViewModel() {
        if (mainViewModel == null)
            mainViewModel = new MainViewModel(publisher, dataFactory.getConfigService(), dataFactory.getTourService());
        return mainViewModel;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        if (searchBarViewModel == null)
            searchBarViewModel = new SearchBarViewModel(publisher);
        return searchBarViewModel;
    }

    public ToursListViewModel getTourListViewModel() {
        if (toursListViewModel == null)
            toursListViewModel = new ToursListViewModel(publisher, dataFactory.getTourService());
        return toursListViewModel;
    }

    public TourViewModel getTourViewModel() {
        if (tourViewModel == null)
            tourViewModel = new TourViewModel(publisher, dataFactory.getTourService());
        return tourViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        if (tourDetailsViewModel == null)
            tourDetailsViewModel = new TourDetailsViewModel(publisher, dataFactory.getTourService(), dataFactory.getOrsRouteService());
        return tourDetailsViewModel;
    }

    public MapViewModel getMapViewModel() {
        if (mapViewModel == null)
            mapViewModel = new MapViewModel(publisher, dataFactory.getTourService());
        return mapViewModel;
    }

    public TourLogsViewModel getTourLogsViewModel() {
        if (tourLogsViewModel == null)
            tourLogsViewModel = new TourLogsViewModel(publisher, dataFactory.getTourService());
        return tourLogsViewModel;
    }

    public TourLogDetailsViewModel getTourLogDetailsViewModel() {
        if (tourLogDetailsViewModel == null)
            tourLogDetailsViewModel = new TourLogDetailsViewModel(publisher, dataFactory.getTourService(), dataFactory.getConfigService());
        return tourLogDetailsViewModel;
    }
}
