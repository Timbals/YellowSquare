package entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import maths.Vector2D;

public class ClueBullet extends Bullet {

	private int maxNextCorrection = 15;
	private int nextCorrection = maxNextCorrection;
	private int lifeSpan = 60 * 2;

	public ClueBullet(int x, int y) {
		super(x, y);
	}

	public ClueBullet(int x, int y, Vector2D velocity) {
		super(x, y, velocity);
	}

	public ClueBullet(int x, int y, Vector2D velocity, double speed) {
		super(x, y, velocity, speed);
	}

	@Override
	public void tick(long delta) {
		super.tick(delta);

		lifeSpan--;
		if (lifeSpan <= 0)
			remove();
		if (lifeSpan <= 20) {
			width = 1;
			height = 1;
		}

		speed = 1;

		nextCorrection--;
		if (nextCorrection <= 0) {
			nextCorrection = maxNextCorrection;

			Vector2D toPlayer = new Vector2D(Player.instance.getX() - getX(), Player.instance.getY() - getY());
			velocity = velocity.add(toPlayer.normalise()).normalise().multiply(speed);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 64, 64));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
