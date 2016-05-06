package graphics;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.ResourceLoader;

import entity.Bullet;
import entity.Entity;
import entity.Player;
import main.GamePanel;
import stage.Map;

public class TileMap {

	public static TileMap instance;

	private int[][] tiles;
	public static int width;
	public static int height;

	public TileMap() {
		width = GamePanel.WIDTH;
		height = GamePanel.HEIGHT;
		tiles = new int[width][height];

		instance = this;
	}

	public void loadMap(String name) {
		try {
			ObjectInputStream oos = new ObjectInputStream(ResourceLoader
					.getResourceAsStream(GamePanel.resfolder + "stages" + File.separator + name + ".map"));
			TileMapData data = (TileMapData) oos.readObject();
			this.tiles = data.tiles;
			width = data.width;
			height = data.height;
			oos.close();

			return;
		} catch (Exception e) {
			System.err.println("Map: '" + name + "' is corrupt");
			e.printStackTrace();
		}
	}

	public void loadEmptyMap() {
		width = 0;
		height = 0;
		tiles = new int[width][height];
	}

	public void saveMap(String name) {
		List<Entity> entities = new ArrayList<Entity>();
		for (Entity e : Entity.getEntities()) {
			if (!(e instanceof Player) && !(e instanceof Bullet))
				entities.add(e);
		}
		TileMapData data = new TileMapData(tiles, width, height);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(Paths.get(GamePanel.resfolder + "stages", name + ".map").toFile()));
			oos.writeObject(data);
			oos.close();

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// not working correctly
		DataOutputStream out = null;

		try {
			out = new DataOutputStream(new FileOutputStream("C:\\Users\\Tim\\Desktop\\" + name + ".map"));

			for (int y = 0; y < height; y++) {
				String s = "";
				for (int x = 0; x < width; x++) {
					int type = getType(x, y);
					s = s + type;
				}
				out.writeChars(s + "\n");
			}
		} catch (Exception e) {
			System.err.println("Error saving map to: " + name);
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				System.err.println("Could not close output stream. Map could have not been saved.");
			}
		}
	}

	public void tick() {

	}

	public void genRoom() {
		genRoom(false);
	}

	public void genRoom(boolean closed) {
		loadMap("template");

		boolean left = Map.getType(Map.x + 1, Map.y) >= Map.UNDISCOVERED;
		boolean right = Map.getType(Map.x - 1, Map.y) >= Map.UNDISCOVERED;
		boolean up = Map.getType(Map.x, Map.y + 1) >= Map.UNDISCOVERED;
		boolean down = Map.getType(Map.x, Map.y - 1) >= Map.UNDISCOVERED;

		if (left && !closed) {
			for (int x = 0; x < 30; x++) {
				for (int y = height / 2 - 10; y < height / 2 + 10; y++) {
					setType(x, y, 0);
				}
			}
		} else if (!left) {
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < height; y++) {
					setType(x, y, 0);
				}
			}
		}

		if (right && !closed) {
			for (int x = width - 12; x < width; x++) {
				for (int y = height / 2 - 10; y < height / 2 + 10; y++) {
					setType(x, y, 0);
				}
			}
		} else if (!right) {
			for (int x = width - 10; x < width; x++) {
				for (int y = 0; y < height; y++) {
					setType(x, y, 0);
				}
			}
		}

		if (up && !closed) {
			for (int x = width / 2 - 10; x < width / 2 + 10; x++) {
				for (int y = 10; y < 12; y++) {
					setType(x, y, 0);
				}
			}
		} else if (!up) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < 10; y++) {
					setType(x, y, 0);
				}
			}
		}

		if (down && !closed) {
			for (int x = width / 2 - 10; x < width / 2 + 10; x++) {
				for (int y = height - 12; y < height; y++) {
					setType(x, y, 0);
				}
			}
		} else if (!down) {
			for (int x = 0; x < width; x++) {
				for (int y = height - 9; y < height; y++) {
					setType(x, y, 0);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.green);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isSolid(x, y)) {
					g.fillRect(x, y, 1, 1);
				}
			}
		}
	}

	public boolean isSolid(int x, int y) {
		try {
			return tiles[x][y] == 1;
		} catch (Exception e) {
			return false;
		}
	}

	public Area getArea() {
		Area area = new Area();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isSolid(x, y)) {
					area.add(new Area(new Rectangle(x, y)));
				}
			}
		}

		return area;
	}

	public int getType(int x, int y) {
		return tiles[x][y];
	}

	public void setType(int x, int y, int type) {
		try {
			tiles[x][y] = type;
		} catch (Exception e) {
			System.err.println(x + " " + y + " out of bounds");
		}
	}

}
