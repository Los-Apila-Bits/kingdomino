package components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static utils.Materials.*;

public class InfoLabel extends Label {

	private final String FONT_PATH = "/resources/playfair_font.ttf";
	
	public InfoLabel(String text, double height, Pos pos, boolean withBackground) {
		Image testImage = new Image(LABEL);
		double width = height * testImage.getWidth() / testImage.getHeight();
		setPrefWidth(width);
		setPrefHeight(height);
		setMinHeight(height);
		setText(" " + text);
		setWrapText(true);
		setLabelFont();
		setAlignment(pos);
		setTextFill(Color.WHITE);
		setPadding(new Insets(10));
		BackgroundImage backgroundImage;	
		if (withBackground) {
			backgroundImage = new BackgroundImage(new Image(LABEL, width, height, false, true), BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
			setBackground(new Background(backgroundImage));
		}
	}
	
	public void setPlayerColor(int playerColor) {
		Image light = new Image(PLAYER_BADGES.get(playerColor));
		double height = getPrefHeight() * 0.75;
		double width = height * light.getWidth() / light.getHeight();
		ImageView imageview = new ImageView (new Image(PLAYER_BADGES.get(playerColor), width, height, false, false));
		setGraphic(imageview);
	}
	
	public void setPointsColor(int playerColor) {
		Image light = new Image(PLAYER_POINTS.get(playerColor));
		double height = getPrefHeight() * 0.75;
		double width = height * light.getWidth() / light.getHeight();
		ImageView imageview = new ImageView (new Image(PLAYER_POINTS.get(playerColor), width, height, false, false));
		setGraphic(imageview);
	}
	
	private void setLabelFont() {
		
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));
		
	}
	

}