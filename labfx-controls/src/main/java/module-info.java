module com.tricolorfire.labfx.controls {
	
	requires java.desktop;
	
	requires transitive javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.web;
	
    opens com.tricolorfire.labfx.control to javafx.fxml;
    
    exports com.tricolorfire.labfx.control;
    exports com.tricolorfire.labfx.dockFX;
}
