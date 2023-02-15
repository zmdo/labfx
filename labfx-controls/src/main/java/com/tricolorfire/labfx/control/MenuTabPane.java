package com.tricolorfire.labfx.control;

import com.tricolorfire.labfx.LayoutBinder;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class MenuTabPane extends TabPane {
	
	public MenuTabPane() {
		super();
		bindFXML();
	}
	
	public MenuTabPane(Tab... tabs) {
		super(tabs);
		bindFXML();
	}

	private void bindFXML(){
		try {
			LayoutBinder.bind(MenuTabPane.class,"menuTabPane/menu-tab-pane.fxml", this, this).load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}