package entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import graphics.Sprite;
import stage.Generator;
import stage.Stage;

public class StairDown extends Entity {

	private Image image;

	public StairDown(int x, int y) {
		super(x, y, 8, 8);
		image = Sprite.get("down.png");
	}

	@Override
	public void tick(long delta) {
		if (getHitbox().intersects(Player.instance.getHitbox())) {
			Generator.generate();
			Stage.transitionStage();
			remove();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, getX(), getY());
	}

}
