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
	private double width;
	private double height;
	private  boolean isHidden;
	
	
	public KDSubScene(double width, double height) {
		super(new AnchorPane(), width, height);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		this.width = width;
		this.height = height;
		prefWidth(width);
		prefHeight(height);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, height, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		
		isHidden = true ;
		
		setLayoutX(screenWidth + width);
		setLayoutY(screenHeight/2 - height/2);
		
	}
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if (isHidden) {
			transition.setToX(-screenWidth - width/2);
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
