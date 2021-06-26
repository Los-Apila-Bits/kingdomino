package components;

import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

public class KDSlider extends StackPane {
	
	private Slider slider;

    public KDSlider(double width, double height) {
        getStylesheets().add(this.getClass().getResource("/resources/colorSlider.css").toExternalForm());
        slider = new Slider();
        setPrefWidth(width);
        setPrefHeight(height);
        slider.setPrefWidth(width);
        slider.setPrefHeight(height);
        slider.setId("color-slider");

        getChildren().addAll(slider);
    }
    
    public void setMin(double value) {
    	slider.setMin(value);
    }
    
    public void setMax(double value) {
    	slider.setMax(value);
    }
    
    public void setValue(double value) {
    	slider.setValue(value);
    }
    
    public double getValue() {
    	return slider.getValue();
    }
    
}