package settings;

public class Settings {
	
	private double musicVolume;
	private boolean fullScreen;
	private double width;
	private double height;
	
	public Settings(double musicVolume, boolean fullScreen, double width, double height) {
		this.musicVolume = musicVolume;
		this.fullScreen = fullScreen;
		this.width = width;
		this.height = height;
	}
	
	public double getMusicVolume() {
		return musicVolume;
	}
	public void setMusicVolume(double musicVolume) {
		this.musicVolume = musicVolume;
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
