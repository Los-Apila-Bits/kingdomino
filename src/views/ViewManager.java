package views;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import settings.Settings;
import javafx.scene.paint.Color;  
import components.KDSubScene;
import components.KDButton;
import components.KDCheckBox;
import components.KDSlider;

import static utils.Sounds.*;


public class ViewManager {
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 25;
	private final static int MENU_BUTTON_START_Y = 100;
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	
	private double height;
	private double width;
	
	private double buttonWidth;
	
	private KDSubScene creditsSubscene;
	private KDSubScene helpSubscene;
	private KDSubScene optionsSubscene;
	
	private Settings settings;
	
	private KDSubScene sceneToHide;
	
	List<KDButton> menuButtons;
	
	
	public ViewManager(Settings settings) {
		this.settings = settings;
		width = settings.getWidth();
		height = settings.getHeight();
		this.buttonWidth = this.width * 0.12;
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, this.width, this.height );
		mainStage = new Stage();
		mainStage.getIcons().add(new Image("/resources/icon.png"));
		mainStage.setScene(mainScene);
		createBackground();
		createLogo();
		createSubScenes();
		createButtons();
		settings.applySettings(mainStage, THEME);
	}
	
	
	private void showSubScene(KDSubScene subScene) {
		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createSubScenes() {
		
		createCreditsSubScene();
		helpCreditsSubScene();
		createOptionsSubScene();
	}
	public void helpCreditsSubScene() {
		helpSubscene = new KDSubScene(0.45);
		mainPane.getChildren().add(helpSubscene);
		double panelWidth = helpSubscene.getWidth();
		double panelHeight = helpSubscene.getHeight();
		Text helpText = new Text();
		GaussianBlur g = new GaussianBlur();  
        g.setRadius(1); 
		helpText.setText("AYUDA");
		helpText.setY(panelHeight * 0.12);  
		helpText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        double textWidth = helpText.getLayoutBounds().getWidth();
        helpText.setX(panelWidth/2 - textWidth/2);
        helpText.setFill(Color.YELLOW);
        helpText.setEffect(g);
        
        
        KDButton exitButton = new KDButton("SALIR", buttonWidth, "green");
        exitButton.setLayoutX(panelWidth/2 - buttonWidth/2);
        exitButton.setLayoutY(panelHeight * 0.8);
        exitButton.setClickSound(CANCEL_SOUND);
        exitButton.setHoverSound(HOVER_SOUND);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				creditsSubscene.moveSubScene();
				sceneToHide = null;
			}
		});
        helpSubscene.getPane().getChildren().addAll(helpText, exitButton);
	}
	
	
	
	public Text createCreditsText(String name, double panelWidth, double panelHeight, GaussianBlur g,
			double heightMultiplier, String align) {

		Text creditsText = new Text();
		creditsText.setText(name);
		creditsText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 26));
		double creditsTextWidth = creditsText.getLayoutBounds().getWidth();
        if(align == "LEFT")
		creditsText.setX(panelWidth * 0.25 - creditsTextWidth/2);
        else if(align == "RIGHT")
        creditsText.setX(panelWidth * 0.75 - creditsTextWidth/2);
        
		creditsText.setY(panelHeight * heightMultiplier);
		creditsText.setFill(Color.YELLOW);
		creditsText.setEffect(g);

		return creditsText;
	}
	
	public void createCreditsSubScene() {
		creditsSubscene = new KDSubScene(0.45);
		mainPane.getChildren().add(creditsSubscene);
		double panelWidth = creditsSubscene.getWidth();
		double panelHeight = creditsSubscene.getHeight();
		Text creditsText = new Text();
		GaussianBlur g = new GaussianBlur();  
        g.setRadius(1); 
		creditsText.setText("CREDITOS");
		creditsText.setY(panelHeight * 0.12);  
		creditsText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        double textWidth = creditsText.getLayoutBounds().getWidth();
        creditsText.setX(panelWidth/2 - textWidth/2);
        creditsText.setFill(Color.YELLOW);
        creditsText.setEffect(g);
        
        Text creditsTextTitle = new Text();  
        creditsTextTitle.setText("LOS APILA BITS");
        creditsTextTitle.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 26));
        double creditsTextWidth = creditsTextTitle.getLayoutBounds().getWidth();
        creditsTextTitle.setX(panelWidth/2 - creditsTextWidth/2);
        creditsTextTitle.setY(panelHeight * 0.20);  
        creditsTextTitle.setFill(Color.YELLOW);
        creditsTextTitle.setEffect(g);
        
        
        List<Text> textList = new ArrayList<Text>();
        
        Text creditsTextNorman = createCreditsText("FUNES NORMAN", panelWidth, panelHeight, g, 0.35, "LEFT");
        textList.add(creditsTextNorman);
        
        Text creditsTextColo = createCreditsText("PAJOT GONZALO", panelWidth, panelHeight, g, 0.45, "LEFT");
        textList.add(creditsTextColo);
        
        Text creditsTextAgata = createCreditsText("AGATA ROCIO", panelWidth, panelHeight, g, 0.55, "LEFT");
        textList.add(creditsTextAgata);
        
        Text creditsTextAgus = createCreditsText("GONZALEZ AGUSTIN", panelWidth, panelHeight, g, 0.65, "LEFT");
        textList.add(creditsTextAgus);
        
        Text creditsTextAlba = createCreditsText("ALBANESI MATIAS", panelWidth, panelHeight, g, 0.35, "RIGHT");
        textList.add(creditsTextAlba);
        
        Text creditsTextLean = createCreditsText("FIORITA LEANDRO", panelWidth, panelHeight, g, 0.45, "RIGHT");
        textList.add(creditsTextLean);
        
        Text creditsTextAilen = createCreditsText("PADILLA AILEN", panelWidth, panelHeight, g, 0.55, "RIGHT");
        textList.add(creditsTextAilen);
        
        Text creditsTextMati = createCreditsText("GALLUÉS MATIAS", panelWidth, panelHeight, g, 0.65, "RIGHT");
        textList.add(creditsTextMati);
        
        for (Text text : textList) {
        	text.setOnMouseEntered(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				text.setEffect(new DropShadow());
    				
    			}
    		});
            
        	text.setOnMouseExited(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				text.setEffect(null);
    				
    			}
    		});
		}
        
        KDButton exitButton = new KDButton("SALIR", buttonWidth, "green");
        exitButton.setLayoutX(panelWidth/2 - buttonWidth/2); 
        exitButton.setLayoutY(panelHeight * 0.8);
        exitButton.setClickSound(CANCEL_SOUND);
        exitButton.setHoverSound(HOVER_SOUND);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				creditsSubscene.moveSubScene();
				sceneToHide = null;
			}
		});
        creditsSubscene.getPane().getChildren().addAll(creditsText, creditsTextTitle, creditsTextAlba, creditsTextMati, creditsTextAilen,
        		creditsTextLean, creditsTextNorman, creditsTextColo, creditsTextAgata, creditsTextAgus, exitButton);
	}
	
	public void createOptionsSubScene() {
		optionsSubscene = new KDSubScene(0.45);
		mainPane.getChildren().add(optionsSubscene);
		double panelWidth = optionsSubscene.getWidth();
		double panelHeight = optionsSubscene.getHeight();
		Text optionsText = new Text();  
		optionsText.setText("OPCIONES");
		optionsText.setY(panelHeight * 0.12);  
		optionsText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        double textWidth = optionsText.getLayoutBounds().getWidth();
        optionsText.setX(panelWidth/2 - textWidth/2);
        optionsText.setFill(Color.YELLOW);  
        GaussianBlur g = new GaussianBlur();  
        g.setRadius(1);  
        optionsText.setEffect(g);
        Text musicText = new Text();  
        musicText.setText("MUSICA");
        musicText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 26));
        double musicTextWidth = musicText.getLayoutBounds().getWidth();
        musicText.setX(panelWidth/2 - musicTextWidth/2);
        musicText.setY(panelHeight * 0.25);  
        musicText.setFill(Color.YELLOW);
        musicText.setEffect(g);
        double sliderWidth = panelWidth/3;
        KDSlider musicSlider = new KDSlider(sliderWidth,panelHeight * 0.02);
        musicSlider.setLayoutY(panelHeight * 0.28);
        musicSlider.setLayoutX(panelWidth /2 - sliderWidth/2);
        musicSlider.setMin(0);
        musicSlider.setMax(1);
        musicSlider.setValue(settings.getMusicVolume());
        double checkBoxSize = panelWidth * .05;
        KDCheckBox resolutionCheckbox = new KDCheckBox(checkBoxSize, "Pantalla Completa");
        resolutionCheckbox.setLayoutY(panelHeight * 0.45);
        resolutionCheckbox.setLayoutX(panelWidth * .1);
        resolutionCheckbox.setChecked(settings.isFullScreen());
        KDCheckBox mouseCheckbox = new KDCheckBox(checkBoxSize, "Cursor Personalizado");
        mouseCheckbox.setLayoutY(panelHeight * 0.55);
        mouseCheckbox.setLayoutX(panelWidth * .1);
        mouseCheckbox.setChecked(settings.isCursorEnabled());
        KDButton acceptButton = new KDButton("ACEPTAR", buttonWidth, "green");
        KDButton cancelButton = new KDButton("CANCELAR", buttonWidth, "green");
        double offSet = buttonWidth * 0.05;
        cancelButton.setLayoutX(panelWidth/2 + offSet);
        cancelButton.setLayoutY(panelHeight * 0.8);
        cancelButton.setClickSound(CANCEL_SOUND);
        cancelButton.setHoverSound(HOVER_SOUND);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				optionsSubscene.moveSubScene();
				sceneToHide = null;
			}
		});
        acceptButton.setLayoutX(panelWidth/2 - buttonWidth - offSet);
        acceptButton.setLayoutY(panelHeight * 0.8);
        acceptButton.setClickSound(CONFIRM_SOUND);
        acceptButton.setHoverSound(HOVER_SOUND);
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean isResolutionChecked = resolutionCheckbox.isChecked();
				boolean isCursorEnabled = mouseCheckbox.isChecked();
				double newMusicVolume = musicSlider.getValue();
				settings.setFullScreen(isResolutionChecked);
				settings.setCursorEnabled(isCursorEnabled);
				settings.setMusicVolume(newMusicVolume);
				settings.applySettings(mainStage, THEME);
				optionsSubscene.moveSubScene();
				sceneToHide = null;
			}
		});
		optionsSubscene.getPane().getChildren().addAll(optionsText, musicText, musicSlider, resolutionCheckbox, mouseCheckbox, acceptButton, cancelButton);
	}
	
	private KDButton createButtonToStart() {
		KDButton startButton = new KDButton("START", buttonWidth, "yellow");
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
		double offSet = (cantButtons * (buttonWidth + MENU_BUTTON_START_X));
		double maxWidth = ((MENU_BUTTON_START_X + buttonWidth) * 5);
		button.setLayoutX(this.width/2 - maxWidth / 2 + offSet);
		button.setLayoutY(this.height - MENU_BUTTON_START_Y);
		button.setClickSound(CLICK_SOUND);
		button.setHoverSound(HOVER_SOUND);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButtons() {
		createStartButton();
		createOptionsButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void createStartButton() {
		KDButton startButton = new KDButton("JUGAR", buttonWidth, "yellow");
		AddMenuButtons(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				showSubScene(shipChooserSubscene);
				ViewPartida partida = new ViewPartida(settings);
				try {
					partida.start(mainStage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void createOptionsButton() {
		KDButton scoresButton = new KDButton("OPCIONES", buttonWidth, "yellow");
		AddMenuButtons(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(optionsSubscene);
				
			}
		});
	}
	
	private void createHelpButton() {
		KDButton helpButton = new KDButton("AYUDA", buttonWidth, "yellow");
		AddMenuButtons(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubscene);
				
			}
		});
	}
	
	private void createCreditsButton() {
		
		KDButton creditsButton = new KDButton("CREDITOS", buttonWidth, "yellow");
		AddMenuButtons(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubscene);
				
			}
		});
	}
	
	private void createExitButton() {
		KDButton exitButton = new KDButton("SALIR", buttonWidth, "yellow");
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