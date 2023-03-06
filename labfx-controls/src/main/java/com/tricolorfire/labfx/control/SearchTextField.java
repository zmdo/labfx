package com.tricolorfire.labfx.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SearchTextField extends BorderPane {

    private TextField textField;
    private ToggleButton filterButton;

    private final DoubleProperty heightProperty = new SimpleDoubleProperty(this,"height",25);
    private final DoubleProperty widthProperty = new SimpleDoubleProperty(this,"width");

    private ListProperty<ObservableList<String>> filterConditionsProperty;

    private BorderPane filterPane;
    private SearchFilterMenu menu;

    public SearchTextField() {
        init();
    }

    private void init() {
        initTextField();
        initToggleButton();
        initFilterPane();
    }

    private void initTextField() {
        textField = new TextField();
        textField.prefHeightProperty().bind(heightProperty);
        textField.setStyle(textFieldStyle(false));
        BorderPane.setAlignment(textField, Pos.CENTER_RIGHT);
        setCenter(textField);
    }

    private void initFilterPane() {

        menu = new SearchFilterMenu();
        BorderPane pane = new BorderPane();
        filterPane = new BorderPane();
        pane.setCenter(filterPane);

        menu.setContent(pane);

        // 绑定监听器
        menu.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                filterButton.selectedProperty().set(t1);
            }
        });
    }

    private void initToggleButton() {
        filterButton = new ToggleButton();

        // 如果不强行绑定，设置图标后会大 1 个像素，需要点击后消除
        filterButton.minHeightProperty().bind(heightProperty);
        filterButton.maxHeightProperty().bind(heightProperty);

        BorderPane.setAlignment(filterButton, Pos.CENTER_LEFT);

        ImageView graphic = new ImageView();
        graphic.fitWidthProperty().bind(heightProperty.multiply(0.6));
        graphic.fitHeightProperty().bind(heightProperty.multiply(0.6));

        try (InputStream stream = SearchTextField.class.getResourceAsStream("/images/filter.png")) {
            graphic.setImage(new Image(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filterButton.setGraphic(graphic);
        filterButton.setTooltip(new Tooltip("筛选"));
        String filterButtonStylesheet =
                Objects.requireNonNull(SearchTextField.class.getResource("searchTextField/filter-button.css")).toExternalForm();
        filterButton.getStylesheets().add(filterButtonStylesheet);

        // 添加形变监听
        filterButton.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                textField.setStyle(textFieldStyle(t1));

                if (t1) {
                    // 计算要出现的位置
                    double x = getAbsoluteX(SearchTextField.this);
                    double y = getAbsoluteY(SearchTextField.this) + heightProperty.get();

                    //  计算宽度
                    double sWidth =  textField.getWidth() + filterButton.getWidth() - 14;
                    filterPane.setMinWidth(sWidth);
                    menu.show(filterButton,x,y);
                } else {
                    menu.hide();
                }

            }
        });

        setRight(filterButton);
    }


    public ListProperty<ObservableList<String>> filterConditionsProperty() {
        return filterConditionsProperty;
    }

    public TextField getTextField() {
        return textField;
    }

    public BorderPane getFilterPane() {
        return filterPane;
    }

    private String textFieldStyle(boolean expend) {
        return String.format(
                "    -fx-background-insets: 1px;" +
                "    -fx-background-color:#ffffff;" +
                "    -fx-border-color:#bbbbbb;" +
                "    -fx-border-width:1px 0px 1px 1px;" +
                "    -fx-border-radius:3px 0px 0px %dpx;"+
                "    -fx-background-color: transparent, white, transparent, white;",
                expend?0:3
        );
    }

    // 递归获取 x 坐标
    public static double getAbsoluteX(Object object) {
        if (object instanceof Stage) {
            Stage Stage = (Stage)object;
            return Stage.getX();
        }
        else if (object instanceof Scene) {
            Scene scene = (Scene)object;
            return scene.getX() + getAbsoluteX(scene.getWindow());
        } else {
            Node node = (Node)object;
            if (node.getParent() == null) {
                return node.getLayoutX() + getAbsoluteX(node.getScene());
            }
            return node.getLayoutX() + getAbsoluteX(node.getParent());
        }
    }

    // 递归获取 y 坐标
    public static double getAbsoluteY(Object object) {
        if (object instanceof Stage) {
            Stage node = (Stage)object;
            return node.getY();
        } else if (object instanceof Scene) {
            Scene node = (Scene)object;
            return node.getY() + getAbsoluteY(node.getWindow());
        } else {
            Node node = (Node)object;
            if (node.getParent() == null) {
                return node.getLayoutY() + getAbsoluteY(node.getScene());
            }
            return node.getLayoutY() + getAbsoluteY(node.getParent());
        }
    }


}
