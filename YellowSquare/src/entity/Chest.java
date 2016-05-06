package entity;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import graphics.Sprite;
import stage.Map;

public class Chest extends Entity {

	private static final Random r = new Random();

	private Image image;
	private boolean opened = false;
	private String upgradeName;
	private int opened_timer = 60 * 2;

	public Chest(int x, int y) {
		super(x, y, 8, 8);

		image = Sprite.get("chest.png");

		int i = 0;
		if (Player.instance.health < Player.instance.maxHealth) {
			i = r.nextInt(3);
		} else {
			i = r.nextInt(2);
		}
		if (i == 0) {
			upgradeName = "ATKSPEED +";
		} else if (i == 1) {
			upgradeName = "BULLETSIZE +";
		} else if (i == 2) {
			upgradeName = "Life +";
		}
	}

	@Override
	public void tick(long delta) {
		if (getHitbox().intersects(Player.instance.getHitbox())) {
			if (!opened) {
				if (upgradeName == "ATKSPEED +") {
					Player.instance.shootSpeed -= 10;
				} else if (upgradeName == "BULLETSIZE +") {
					Player.instance.bulletSize++;
				} else if (upgradeName == "Life +") {
					Player.instance.health++;
				}
				opened = true;
				Map.setChest(false);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (opened) {
			opened_timer--;
			g.setColor(Color.white);
			g.drawString(upgradeName,
					Player.instance.getX() - g.getFont().getWidth(upgradeName) / 2 + Player.instance.getWidth() / 2,
					Player.instance.getY() - g.getFont().getHeight(upgradeName));
			if (opened_timer <= 0)
				remove();
		} else {
			g.drawImage(image, getX(), getY());
		}
	}

}
