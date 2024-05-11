package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.viewmodel.MainViewModel;

public class ViewModelFactory {
    private static ViewModelFactory instance;

    // Factories
    private final ModelFactory modelFactory;

    // View-Models
    private MainViewModel mainViewModel;

    private ViewModelFactory(ModelFactory modelFactory) {
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
            mainViewModel = new MainViewModel(modelFactory.getModel());
        return mainViewModel;
    }
}
