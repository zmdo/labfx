package com.tricolorfire.labfx.control;

import com.tricolorfire.labfx.LayoutBinder;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


/**
 * <p>菜单单元</p>
 * <p>该菜单单元是{@link com.tricolorfire.labfx.control.MenuTabPane}的Tab标签下的标准单元</p>
 */
public class MenuUnit extends BorderPane{
	
	@FXML private Label titleLabel;  // 单元标题
	@FXML private HBox contentHBox;  // HBox容器
	
	public MenuUnit() {
		super();
		init(null);
	}
	
	public MenuUnit(String title) {
		super();
		init(title);
	}
	
	private void init(String title) {
        try {
			LayoutBinder.bind(MenuUnit.class,"menuUnit/menu-unit.fxml", this, this).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(title != null) {
        	setTitle(title);
        }
        
	}
	
	public StringProperty titleProperty() {
		return titleLabel.textProperty();
	}
	
	public void setTitle(String title) {
		titleProperty().set(title);
	}
	
	public String getTitle(){
		return titleProperty().get();
	}
	
	public ObservableList<Node> getContainer(){
		return contentHBox.getChildren();
	}
	
}