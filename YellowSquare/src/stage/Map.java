package stage;

import java.awt.Point;

import entity.Entity;
import entity.Player;

public class Map {

	public static final int NONE = 0;
	public static final int UNDISCOVERED = 1;
	public static final int DISCOVERED = 2;
	public static final int CLEARED = 3;

	public static final int LEVEL_SIZE = 256; // max size of level

	public static int x = LEVEL_SIZE / 2; // current x and y coordinates
	public static int y = LEVEL_SIZE / 2;

	private static int blink = 30;
	public static boolean visible = true;

	private static int[][] stages = new int[LEVEL_SIZE][LEVEL_SIZE];
	private static boolean[][] chests = new boolean[LEVEL_SIZE][LEVEL_SIZE];
	public static Point boss;

	public static int difficulty = 0;

	public static void reset() {
		stages = new int[LEVEL_SIZE][LEVEL_SIZE];
		chests = new boolean[LEVEL_SIZE][LEVEL_SIZE];
		x = LEVEL_SIZE / 2;
		y = LEVEL_SIZE / 2;

		boss = new Point(x, y);

		difficulty += 5;

		for (int i = Entity.getEntities().size() - 1; i >= 0; i--) {
			Entity e = Entity.getEntities().get(i);
			if (!(e instanceof Player)) {
				e.remove();
			}
		}
	}

	public static void tick() {
		blink--;
		if (blink <= 0) {
			blink = 30;
			visible = !visible;
		}
	}

	public static void discover() {
		if (Map.getType(Map.x + 1, Map.y) == Map.UNDISCOVERED)
			Map.setType(Map.DISCOVERED, Map.x + 1, Map.y);
		if (Map.getType(Map.x - 1, Map.y) == Map.UNDISCOVERED)
			Map.setType(Map.DISCOVERED, Map.x - 1, Map.y);
		if (Map.getType(Map.x, Map.y + 1) == Map.UNDISCOVERED)
			Map.setType(Map.DISCOVERED, Map.x, Map.y + 1);
		if (Map.getType(Map.x, Map.y - 1) == Map.UNDISCOVERED)
			Map.setType(Map.DISCOVERED, Map.x, Map.y - 1);
	}

	public static void discoverAll() {
		for (int x = 0; x < LEVEL_SIZE; x++) {
			for (int y = 0; y < LEVEL_SIZE; y++) {
				if (getType(x, y) == UNDISCOVERED)
					setType(Map.DISCOVERED, x, y);
			}
		}
	}

	public static boolean isDiscovered(int x, int y) {
		return stages[x][y] > 1;
	}

	public static int getType(int x, int y) {
		return stages[x][y];
	}

	public static void setType(int i) {
		stages[x][y] = i;
	}

	public static void setType(int i, int x, int y) {
		stages[x][y] = i;
	}

	public static void setBossRoom(int x, int y) {
		setBossRoom(new Point(x, y));
	}

	public static void setBossRoom(Point p) {
		boss = p;
	}

	public static boolean isBossRoom() {
		return boss.x == x && boss.y == y;
	}

	public static boolean isClear() {
		return stages[x][y] > 2;
	}

	public static void setChest(int x, int y, boolean chest) {
		chests[x][y] = chest;
	}

	public static void setChest(boolean chest) {
		setChest(x, y, chest);
	}

	public static boolean isChest(int x, int y) {
		return chests[x][y];
	}

	public static boolean isChest() {
		return isChest(x, y);
	}

}
