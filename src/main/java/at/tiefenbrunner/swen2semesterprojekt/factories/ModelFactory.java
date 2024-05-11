package at.tiefenbrunner.swen2semesterprojekt.factories;

import at.tiefenbrunner.swen2semesterprojekt.model.DataModel;
import at.tiefenbrunner.swen2semesterprojekt.model.Model;

public class ModelFactory {

    private static ModelFactory instance;

    private Model model;

    private ModelFactory() {
    }

    public static ModelFactory getInstance() {
        if (null == instance) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public Model getModel() {
        if(model == null)
            model = new DataModel();
        return model;
    }
}
