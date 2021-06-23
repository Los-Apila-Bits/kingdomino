package components;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static utils.ButtonsPaths.*;

public class KDButton extends Button{
	
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	private String color;
	
	public KDButton(String text, double width, String color) {
		this.color = color;
		Image backgroundImage = new Image(BUTTONS.get(color));
		double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double height = width * imageHeight / imageWidth;
		setText(text);
		setButtonFont();
		setPrefWidth(width);
		setPrefHeight(height);
		setTextFill(Color.WHITE);
		setStyle("-fx-background-color: transparent; -fx-background-size: cover; -fx-background-image: url('"+BUTTONS.get(color)+"');");
		initializeButtonListeners();
		
	}

	private void setButtonFont() {
		
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30));
		
	}
	
	private void setButtonPressedStyle() {
		setStyle("-fx-background-color: transparent; -fx-background-size: cover; -fx-background-image: url('"+BUTTONS.get(color+"Pressed")+"');");
		setLayoutY(getLayoutY() + 4);
		
	}
	
	private void setButtonReleasedStyle() {
		setStyle("-fx-background-color: transparent; -fx-background-size: cover; -fx-background-image: url('"+BUTTONS.get(color)+"');");
		setLayoutY(getLayoutY() - 4);
		
	}
	
	private void initializeButtonListeners() {
		
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
				}
				
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
				}
				
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
				
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
				
			}
		});
	
	}

}