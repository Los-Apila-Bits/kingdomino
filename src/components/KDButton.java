package components;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static utils.ButtonsPaths.*;
import static utils.Materials.GAME_PANEL_2;

public class KDButton extends Button{
	
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	private String color;
	private AudioClip clickSound;
	private AudioClip hoverSound;
	private double width;
	private double height;
	
	public KDButton(String text, double width, String color, int tamFont) {
		this.color = color;
		Image backgroundImage = new Image(BUTTONS.get(color));
		double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		this.width = width;
		this.height = width * imageHeight / imageWidth;
		BackgroundImage background = new BackgroundImage(new Image(BUTTONS.get(color), width, height, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		setBackground(new Background(background));
		setText(text);
		setButtonFont(tamFont);
		setPrefWidth(width);
		setPrefHeight(height);
		setTextFill(Color.WHITE);
		initializeButtonListeners();
		
	}

	private void setButtonFont(int tamFont) {
		
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), tamFont));
		
	}
	
	private void setButtonPressedStyle() {
		BackgroundImage background = new BackgroundImage(new Image(BUTTONS.get(color+"Pressed"), width, height, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		setBackground(new Background(background));
		setLayoutY(getLayoutY() + 4);
		
	}
	
	private void setButtonReleasedStyle() {
		BackgroundImage background = new BackgroundImage(new Image(BUTTONS.get(color), width, height, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		setBackground(new Background(background));
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