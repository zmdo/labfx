package com.tricolorfire.labfx;

import javafx.fxml.FXMLLoader;

import java.net.URL;

/**
 * FXML 绑定器
 *
 */
public class LayoutBinder {
	
	public static FXMLLoader bind(URL url,Object root,Object controller) {
		FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(controller);
		return fxmlLoader;
	}
	
	public static FXMLLoader bind(Class<?> clazz,String filename,Object root,Object controller) {
		return bind(clazz.getResource(filename),root,controller);
	}
	
	public static FXMLLoader bind(String className,String filename,Object root,Object controller) throws ClassNotFoundException {
		return bind(Class.forName(className),filename,root,controller);
	}
	
}
