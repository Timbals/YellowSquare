package editor;

import javax.swing.JOptionPane;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import entity.Entity;
import graphics.TileMap;
import main.GamePanel;

public class Editor {

	public static final boolean enabled = false;

	public static void tick() {
		if (!enabled)
			return;

		Entity.getEntities().clear();

		if (GamePanel.in.isMouseButtonDown(0)) {
			TileMap.instance.setType(GamePanel.in.getMouseX(), GamePanel.in.getMouseY(), 1);
		} else if (GamePanel.in.isMouseButtonDown(1)) {
			TileMap.instance.setType(GamePanel.in.getMouseX(), GamePanel.in.getMouseY(), 0);
		}

		if (GamePanel.in.isKeyPressed(Input.KEY_O)) {
			String name = JOptionPane.showInputDialog("Save stage");
			TileMap.instance.saveMap(name);
			return;
		} else if (GamePanel.in.isKeyPressed(Input.KEY_P)) {
			String name = JOptionPane.showInputDialog("Opem stage");
			TileMap.instance.loadMap(name);
			return;
		}
	}

	public static void render(Graphics g) {
		if (!enabled)
			return;
	}

}
