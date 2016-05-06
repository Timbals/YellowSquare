package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import graphics.Sprite;
import main.GamePanel;
import stage.Map;

public class TutorialOverlay {

	public static void render(Graphics g) {
		Image image = Sprite.get("tutoverlay.png");
		g.drawImage(image, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, 0, 0, image.getWidth(), image.getHeight());

		Image level = Sprite.get("level1.png");

		if (Map.difficulty / 5 == 2) {
			level = Sprite.get("level2.png");
		} else if (Map.difficulty / 5 == 3) {
			level = Sprite.get("level3.png");
		} else if (Map.difficulty / 5 == 4) {
			level = Sprite.get("level4.png");
		}

		g.drawImage(level, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, 0, 0, level.getWidth(), level.getHeight());
	}

}
