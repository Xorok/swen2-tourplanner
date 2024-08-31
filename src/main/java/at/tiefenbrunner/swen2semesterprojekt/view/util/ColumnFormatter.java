package at.tiefenbrunner.swen2semesterprojekt.view.util;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.text.Format;

/*
 * From https://stackoverflow.com/a/38967170
 */
public class ColumnFormatter<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final Format format;

    public ColumnFormatter(Format format) {
        super();
        this.format = format;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> arg0) {
        return new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new Label(format.format(item)));
                }
            }
        };
    }
}