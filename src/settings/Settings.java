package settings;

import static utils.Materials.*;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Settings {
	
	
	
	private double musicVolume;
	private boolean fullScreen;
	private double width;
	private double height;
	private boolean cursorEnabled;
	private EventHandler<MouseEvent> cursorClicked;
	private EventHandler<MouseEvent> cursorReleased;
	public MediaPlayer themeSong;
	
	public Settings(double musicVolume, boolean fullScreen, double width, double height) {
		this.musicVolume = musicVolume;
		this.fullScreen = fullScreen;
		this.width = width;
		this.height = height;
		this.cursorEnabled = true;
	}
	
	public void applySettings(Stage stage, String musicPath) {
		Scene scene = stage.getScene();
		if (cursorClicked != null && cursorReleased != null) {
			scene.removeEventFilter(MouseEvent.MOUSE_PRESSED, cursorClicked);
			scene.removeEventFilter(MouseEvent.MOUSE_RELEASED, cursorReleased);
		}
		if (cursorEnabled) {
			Image defaultMouse = new Image(CURSOR_STATIC);
			Image clickMouse = new Image(CURSOR_CLICK);
			scene.setCursor(new ImageCursor(defaultMouse));
			cursorClicked = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			    	scene.setCursor(new ImageCursor(clickMouse));
			    }
			};
			scene.addEventFilter(MouseEvent.MOUSE_PRESSED, cursorClicked);
			cursorReleased = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        // on click actions here
			    	scene.setCursor(new ImageCursor(defaultMouse));
			    }
			};
			scene.addEventFilter(MouseEvent.MOUSE_RELEASED, cursorReleased);
		}
		else {
			scene.setCursor(Cursor.DEFAULT);
			cursorClicked = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			    	scene.setCursor(Cursor.DEFAULT);
			    }
			};
			scene.addEventFilter(MouseEvent.MOUSE_PRESSED, cursorClicked);
			cursorReleased = new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        // on click actions here
			    	scene.setCursor(Cursor.DEFAULT);
			    }
			};
			scene.addEventFilter(MouseEvent.MOUSE_RELEASED, cursorReleased);
		}
		stage.setFullScreen(fullScreen);
		if (themeSong != null) themeSong.stop();
		Media sound = new Media(getClass().getResource(musicPath).toExternalForm());
		themeSong = new MediaPlayer(sound);
		themeSong.setAutoPlay(true);
		themeSong.setVolume(musicVolume);
		themeSong.setOnEndOfMedia(new Runnable() {
	        @Override
	        public void run() {
	        	themeSong.seek(Duration.ZERO);
	        	themeSong.play();
	        }
	    }); 
		themeSong.play();
	}
	
	public double getMusicVolume() {
		return musicVolume;
	}
	public void setMusicVolume(double musicVolume) {
		this.musicVolume = musicVolume;
	}
	public boolean isCursorEnabled() {
		return cursorEnabled;
	}
	public void setCursorEnabled(boolean cursorEnabled) {
		this.cursorEnabled = cursorEnabled;
	}
	public boolean isFullScreen() {
		return fullScreen;
	}
	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}

}
