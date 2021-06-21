package views;


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
	
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;
	
	
	private KDSubScene creditsSubscene;
	private KDSubScene helpSubscene;
	private KDSubScene scoreSubscene;
	private KDSubScene shipChooserSubscene;
	
	private KDSubScene sceneToHide;
	
	
	
	
	List<KDButton> menuButtons;
	
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT );
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createSubScenes();
		CreateButtons();
		createBackground();
		createLogo();
	
		
	}
	
	
	private void showSubScene(KDSubScene subScene) {
		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	
	
	
	private void createSubScenes() {
		
		creditsSubscene = new KDSubScene();
		mainPane.getChildren().add(creditsSubscene);
		helpSubscene = new KDSubScene();
		mainPane.getChildren().add(helpSubscene);
		scoreSubscene = new KDSubScene();
		mainPane.getChildren().add(scoreSubscene);

		
		createShipChooserSubScene();
	
	}

	
	private void createShipChooserSubScene() {
		shipChooserSubscene = new KDSubScene();
		mainPane.getChildren().add(shipChooserSubscene);
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		shipChooserSubscene.getPane().getChildren().add(chooseShipLabel);
		shipChooserSubscene.getPane().getChildren().add(chooseShipLabel);
		shipChooserSubscene.getPane().getChildren().add(createButtonToStart());
		
		
		
	}
	
	
	
	private KDButton createButtonToStart() {
		KDButton startButton = new KDButton("START");
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
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
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
		KDButton startButton = new KDButton("PLAY");
		AddMenuButtons(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(shipChooserSubscene);
				
			}
		});
	}
	
	private void createScoresButton() {
		KDButton scoresButton = new KDButton("SCORES");
		AddMenuButtons(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoreSubscene);
				
			}
		});
	}
	
	private void createHelpButton() {
		KDButton helpButton = new KDButton("HELP");
		AddMenuButtons(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubscene);
				
			}
		});
	}
	
	private void createCreditsButton() {
		
		KDButton creditsButton = new KDButton("CREDITS");
		AddMenuButtons(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubscene);
				
			}
		});
	}
	
	private void createExitButton() {
		KDButton exitButton = new KDButton("EXIT");
		AddMenuButtons(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				
			}
		});
		
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("/resources/deep_blue.png", 256, 256, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("/resources/space_runner.png");
		logo.setLayoutX(380);
		logo.setLayoutY(50);
		
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
