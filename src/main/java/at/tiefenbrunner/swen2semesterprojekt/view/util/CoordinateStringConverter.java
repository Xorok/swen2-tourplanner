package at.tiefenbrunner.swen2semesterprojekt.view.util;

import at.tiefenbrunner.swen2semesterprojekt.util.Constants;
import javafx.util.converter.NumberStringConverter;

import java.text.DecimalFormatSymbols;

// TODO: Extract conversion logic outside of View
public class CoordinateStringConverter extends NumberStringConverter {
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();

    public CoordinateStringConverter() {
        super(Constants.COORDINATE_FORMAT);
    }

    @Override
    public Number fromString(String value) {
        value = value.replace('.', dfs.getDecimalSeparator());
        value = value.replace(',', dfs.getDecimalSeparator());
        return super.fromString(value);
    }
}
