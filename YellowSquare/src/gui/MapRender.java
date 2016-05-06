package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import stage.Map;

public class MapRender {

	private static final int posX = 13; // position and size of map rendering
	private static final int posY = 13;
	private static final int width = 30;
	private static final int height = 20;
	private static final int transparency = 255;
	private static final Color alphaWhite = new Color(255, 255, 255, transparency);
	private static final Color alphaGreen = new Color(0, 255, 0, transparency);
	private static final Color alphaRed = new Color(255, 0, 0, transparency);

	public static void render(Graphics g) {
		g.setColor(alphaWhite);
		g.fillRect(posX + 1, posY, width - 2, 1);
		g.fillRect(posX + 1, height + posY - 3, width - 2, 1);
		g.fillRect(posX, posY, 1, height - 2);
		g.fillRect(posX - 1 + width, posY, 1, height - 2);

		for (int nx = Map.x - width / 2 / 3 + 1; nx < Map.x + width / 2 - width / 3; nx++) {
			for (int ny = Map.y - height / 2 / 3 + 1; ny < Map.y + height / 2 - height / 3 - 1; ny++) {
				if ((!(nx == Map.x && ny == Map.y) || Map.visible) && Map.isDiscovered(nx, ny)) {
					if (Map.getType(nx, ny) == Map.DISCOVERED)
						g.setColor(alphaWhite);
					if (Map.getType(nx, ny) == Map.CLEARED)
						g.setColor(alphaGreen);
					if (Map.boss.x == nx && Map.boss.y == ny)
						g.setColor(alphaRed);
					g.fillRect(posX + width / 2 - 1 + (Map.x - nx) * 3, posY - 1 + height / 2 - 1 + (Map.y - ny) * 3, 2,
							2);
				}
			}
		}
	}
}
