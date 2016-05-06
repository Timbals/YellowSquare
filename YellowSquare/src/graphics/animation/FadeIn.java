package graphics.animation;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import graphics.Sprite;
import main.GamePanel;

public class FadeIn extends Animation {

	private Image menu;
	private Color[][] colors;

	@Override
	protected void tick() {
	}

	@Override
	protected void render(Graphics g) {
		try {
			if (menu == null) {
				menu = Sprite.get("menu.png");
				colors = new Color[menu.getWidth()][menu.getHeight()];
				for (int x = 0; x < GamePanel.WIDTH; x++) {
					for (int y = 0; y < GamePanel.HEIGHT; y++) {
						colors[x][y] = menu.getColor(x, y);
					}
				}
			}

			for (int x = 0; x < GamePanel.WIDTH; x++) {
				for (int y = 0; y < GamePanel.HEIGHT; y++) {
					g.setColor(Color.white);
					if (colors[x][y].a > 0.1)
						g.setColor(colors[x][y]);
					if (isBlack(x, y)) {
						g.fillRect(x, y, 1, 1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isBlack(int x, int y) {
		if (x >= 0 + GamePanel.WIDTH / 2 - (tick - 5) && x <= 0 + GamePanel.WIDTH / 2 + (tick - 5))
			return false;
		// if (y >= 0 + GamePanel.HEIGHT / 2 - tick && y <= 0 + GamePanel.HEIGHT
		// / 2 + tick)
		// return false;
		return true;
	}

}
