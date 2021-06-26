package components;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static utils.ButtonsPaths.*;

public class KDButton extends Button{
	
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	private String color;
	private AudioClip clickSound;
	private AudioClip hoverSound;
	
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
	
	public void setHoverSound(String hoverSound) {
		this.hoverSound = new AudioClip(this.getClass().getResource(hoverSound).toString());;
	}
	
	public void setClickSound(String clickSound) {
		this.clickSound = new AudioClip(this.getClass().getResource(clickSound).toString());
	}
	
	private void initializeButtonListeners() {
		
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
					if (clickSound != null) {
						clickSound.play();
					}
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
				if (hoverSound != null) {
					hoverSound.play();
				}
				
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