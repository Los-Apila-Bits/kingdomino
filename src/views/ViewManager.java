package views;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import components.InfoLabel;
import components.KDSubScene;
import components.KDButton;


public class ViewManager {
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 25;
	private final static int MENU_BUTTON_START_Y = 100;
	private final static int BUTTON_WIDTH = 190;
	
	private double height;
	private double width;
	
	private KDSubScene creditsSubscene;
	private KDSubScene helpSubscene;
	private KDSubScene scoreSubscene;
	private KDSubScene shipChooserSubscene;
	
	private KDSubScene sceneToHide;
	
	
	
	
	List<KDButton> menuButtons;
	
	public ViewManager() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.width = screenSize.getWidth();
		this.height = screenSize.getHeight();
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, this.width, this.height );
		mainStage = new Stage();
		mainStage.getIcons().add(new Image("/resources/icon.png"));
		mainStage.setScene(mainScene);
		mainStage.setFullScreen(true);
		createBackground();
		createLogo();
		createSubScenes();
		CreateButtons();
	}
	
	
	private void showSubScene(KDSubScene subScene) {
		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createSubScenes() {
		
		creditsSubscene = new KDSubScene(width/2, height/2);
		mainPane.getChildren().add(creditsSubscene);
		helpSubscene = new KDSubScene(width/2, height/2);
		mainPane.getChildren().add(helpSubscene);
		scoreSubscene = new KDSubScene(width/2, height/2);
		mainPane.getChildren().add(scoreSubscene);
	
	}
	
	private KDButton createButtonToStart() {
		KDButton startButton = new KDButton("START", BUTTON_WIDTH);
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		return startButton;
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void AddMenuButtons(KDButton button) {
		int cantButtons = menuButtons.size();
		int offSet = (cantButtons * (BUTTON_WIDTH + MENU_BUTTON_START_X));
		int maxWidth = ((MENU_BUTTON_START_X + BUTTON_WIDTH) * 5);
		button.setLayoutX(this.width/2 - maxWidth / 2 + offSet);
		button.setLayoutY(this.height - MENU_BUTTON_START_Y);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	
	
	private void CreateButtons() {
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void createStartButton() {
		KDButton startButton = new KDButton("PLAY", BUTTON_WIDTH);
		AddMenuButtons(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				showSubScene(shipChooserSubscene);
				ViewPartida partida = new ViewPartida(height, width);
				try {
					partida.start(mainStage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void createScoresButton() {
		KDButton scoresButton = new KDButton("OPTIONS", BUTTON_WIDTH);
		AddMenuButtons(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoreSubscene);
				
			}
		});
	}
	
	private void createHelpButton() {
		KDButton helpButton = new KDButton("HELP", BUTTON_WIDTH);
		AddMenuButtons(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubscene);
				
			}
		});
	}
	
	private void createCreditsButton() {
		
		KDButton creditsButton = new KDButton("CREDITS", BUTTON_WIDTH);
		AddMenuButtons(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubscene);
				
			}
		});
	}
	
	private void createExitButton() {
		KDButton exitButton = new KDButton("EXIT", BUTTON_WIDTH);
		AddMenuButtons(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				
			}
		});
		
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("/resources/background.jpg", this.width, this.height, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("/resources/kingdomino_apilabits.png");
		double logoSize = this.width/2.5;
		logo.setLayoutX(this.width/2-logoSize/2);
		logo.setLayoutY(50);
		logo.setFitWidth(logoSize);
		logo.setPreserveRatio(true);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
				
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
				
			}
		});
		
		mainPane.getChildren().add(logo);
		
	}

}
