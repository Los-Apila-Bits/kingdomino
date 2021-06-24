package components;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import static utils.Materials.*;

public class KDCheckBox extends HBox {
	
	private CheckBox checkbox;
	private Text text;
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	
	public KDCheckBox(double width, String label) {
		Image backgroundImage = new Image(CHECKED);
		double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double height = width * imageHeight / imageWidth;
		setSpacing(20);
		checkbox = new CheckBox();
		checkbox.setPrefWidth(width);
		checkbox.setPrefHeight(height);
		setCheckedStyle();
		checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue)
		    	setCheckedStyle();
		    else
		    	setUnCheckedStyle();
		});
		text = new Text();  
        text.setText(label);
        text.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 26));
        text.setFill(Color.WHITE); 
		getChildren().addAll(checkbox,text);
		
	}
	
	private void setCheckedStyle() {
		checkbox.setStyle("-fx-pressed-base: transparent; -fx-hover-base: transparent; -fx-color: transparent; -fx-mark-highlight-color: transparent; -fx-mark-color: transparent; -fx-focused-mark-color: transparent; -fx-focus-color: transparent; -fx-inner-border: transparent; -fx-faint-focus-color: transparent; -fx-shadow-highlight-color: transparent; -fx-body-color: transparent; -fx-background-color: transparent; -fx-background-size: cover; -fx-shape: none; -fx-background-image: url('"+CHECKED+"');");
		
	}
	
	private void setUnCheckedStyle() {
		checkbox.setStyle("-fx-pressed-base: transparent; -fx-hover-base: transparent; -fx-color: transparent; -fx-mark-highlight-color: transparent; -fx-mark-color: transparent; -fx-focused-mark-color: transparent; -fx-focus-color: transparent; -fx-inner-border: transparent; -fx-faint-focus-color: transparent; -fx-shadow-highlight-color: transparent; -fx-body-color: transparent; -fx-background-color: transparent; -fx-background-size: cover; -fx-shape: none; -fx-background-image: url('"+UNCHECKED+"');");
		
	}
	
	public void setChecked(boolean checked) {
		checkbox.setSelected(checked);
	}
	
	public boolean isChecked() {
		return checkbox.isSelected();
	}

}