package components;

import java.awt.*;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class KDSubScene extends SubScene{
	
	private final static String FONT_PATH = "/resources/morris_font.ttf";
	private final static String BACKGROUND_IMAGE = "/resources/window.png";
	
	private double screenWidth;
	private double screenHeight;
	private boolean isHidden;
	
	
	public KDSubScene(double scale) {
		super(new AnchorPane(), 0, 0);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		scaleScene(scale);
		isHidden = true ;
	}
	
	public void scaleScene(double scale) {
		Image backgroundImage = new Image(BACKGROUND_IMAGE);
		double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double width = screenWidth * scale;
		double height = width * imageHeight / imageWidth;
		prefWidth(width);
		prefHeight(height);
		setWidth(width);
		setHeight(height);
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, height, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		isHidden = true ;
		setLayoutX(screenWidth + screenWidth/2 - width/2);
		setLayoutY(screenHeight/1.9 - height/2);;
	}
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if (isHidden) {
			transition.setToX(-screenWidth);
			isHidden = false;
			
		} else {
			transition.setToX(0);
			isHidden = true ;
		}
		
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}
