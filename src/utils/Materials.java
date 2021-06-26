package utils;
import java.util.HashMap;
import java.util.Map;

public class Materials {

	 public static final String CHECKED = "/resources/checked.png";
	 public static final String UNCHECKED = "/resources/unchecked.png";
	 public static final String CURSOR_STATIC = "/resources/cursorSword_silver.png";
	 public static final String CURSOR_CLICK = "/resources/cursorSword_gold.png";
	 public static final String WINDOW = "/resources/window.png";
	 public static final String GAME_PANEL_1 = "/resources/game_panel_1.png";
	 public static final String GAME_PANEL_2 = "/resources/game_panel_2.png";
	 public static final String GAME_ICON = "/resources/icon.png";
	 public static final String BOARD_MAP = "/resources/board_map.png";
	 public static final String CASILLA = "/resources/casilla.png";
	 public static final String LABEL = "/resources/label.png";
	 public static final String FICHA_PAPER = "/resources/ficha_paper.png";
	 public static final Map<Integer, String> PLAYER_BADGES = new HashMap<>();
	 public static final Map<Integer, String> PLAYER_POINTS = new HashMap<>();
	 
	 static {
		 PLAYER_BADGES.put(PlayerColor.RED, "/resources/red_player_on.png");
		 PLAYER_BADGES.put(PlayerColor.GREEN, "/resources/green_player_on.png");
	 }
	 
	 static {
		 PLAYER_POINTS.put(PlayerColor.RED, "/resources/red_points.png");
		 PLAYER_POINTS.put(PlayerColor.GREEN, "/resources/green_points.png");
	 }
	 
}