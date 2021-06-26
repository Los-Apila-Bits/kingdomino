package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.stage.Stage;
import settings.Settings;
import views.ViewManager;


public class App extends Application {
	 
	private static final double INITIAL_MUSICVOLUME = 0.5;
	private static final boolean INITIAL_FULLSCREEN = true;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();
			Settings settings = new Settings(INITIAL_MUSICVOLUME,INITIAL_FULLSCREEN,width,height);
			ViewManager manager = new ViewManager(settings);
			primaryStage = manager.getMainStage();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


