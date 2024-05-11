package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.view.MainView;

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

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
