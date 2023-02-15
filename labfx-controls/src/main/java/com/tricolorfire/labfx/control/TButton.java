package com.tricolorfire.labfx.control;

import com.tricolorfire.labfx.LayoutBinder;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * <p>背景透明的按钮控件</p>
 * 
 */
public class TButton extends Button {

	/**
	 * 创建一个标题为空字符串的透明按钮
	 */
	public TButton() {
		this(null);
	}

	/**
	 * 创建一个标题为 text 的透明按钮
	 * 
	 * @param text
	 */
	public TButton(String text) {
		super();
		bindFXML(text);
	}
	
	private void bindFXML(String text){
        try {
			LayoutBinder.bind(TButton.class,"tbutton/tbutton.fxml", this, this).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(text != null) {
			setText(text);
		}
	}
	
}
