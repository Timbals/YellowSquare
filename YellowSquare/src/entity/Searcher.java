package entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import graphics.Sprite;
import maths.Vector2D;

public class Searcher extends Entity {

	private static final double speed = 0.8;
	private static Image image;

	public Searcher(int x, int y) {
		super(x, y, 6, 6);

		if (image == null)
			image = Sprite.get("searcher.png");
	}

	@Override
	public void tick(long delta) {
		velocity = new Vector2D(Player.instance.getX() - getX(), Player.instance.getY() - getY()).normalise()
				.multiply(speed);
		calculatePosition();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0, 0, image.getWidth(),
				image.getHeight());
	}

	@Override
	protected boolean onBulletHit(boolean isPlayer) {
		return isPlayer;
	}

}
