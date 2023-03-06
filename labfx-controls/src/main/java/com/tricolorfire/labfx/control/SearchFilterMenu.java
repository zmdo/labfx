package com.tricolorfire.labfx.control;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;

public class SearchFilterMenu extends ContextMenu {

    private CustomMenuItem mainItem;

    public SearchFilterMenu() {
        init();
    }

    private void init() {
        mainItem = new CustomMenuItem();
        mainItem.setHideOnClick(false);
        getItems().add(mainItem);
    }

    public void setContent(Node node) {
        mainItem.setContent(node);
    }

}
