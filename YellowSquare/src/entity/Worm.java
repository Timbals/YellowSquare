package entity;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import maths.Vector2D;

public class Worm extends Entity {

	private static final int gap = 8;
	private static final int amount = 3;
	private static Random r = new Random();

	private ArrayList<WormPart> parts = new ArrayList<WormPart>();
	private int nextChange = 20;

	public Worm(int x, int y) {
		super(x, y, 8, 8);
		changeDirection();

		for (int i = 0; i < amount; i++) {
			parts.add(new WormPart(getX(), getY(), this));
		}
	}

	@Override
	public void tick(long delta) {
		nextChange--;
		if (nextChange <= 0) {
			nextChange = r.nextInt(120) + 60;
			changeDirection();
		}

		Vector2D vec = getVelocity().multiply(1);
		calculatePosition();
		while (velocity.getX() == 0 && velocity.getY() == 0) {
			if (vec.getX() != 0) {
				changeDirection();
			} else {
				changeDirection();
			}
			calculatePosition();
		}

		for (int i = 0; i < parts.size(); i++) {
			WormPart part = parts.get(i);
			if (!Entity.getEntities().contains(part)) {
				parts.remove(part);
				i--;
				continue;
			}
			part.setTargetOffX((int) ((gap * i + gap) * velocity.getX()));
			part.setTargetOffY((int) ((gap * i + gap) * velocity.getY()));
		}

		if (parts.isEmpty())
			remove();
	}

	private void changeDirection() {
		int direction = r.nextInt(4);

		switch (direction) {
		case 0:
			velocity = new Vector2D(getSpeed(), 0);
			break;
		case 1:
			velocity = new Vector2D(-getSpeed(), 0);
			break;
		case 2:
			velocity = new Vector2D(0, getSpeed());
			break;
		case 3:
			velocity = new Vector2D(0, -getSpeed());
			break;
		default:
			break;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void remove() {
		super.remove();
		for (WormPart part : parts) {
			part.remove();
		}
		parts.clear();
	}

	@Override
	protected boolean onBulletHit(boolean isPlayer) {
		return isPlayer;
	}

	public double getSpeed() {
		return 1;
	}

	class WormPart extends Entity {

		private static final int WIDTH = 6;
		private static final int HEIGHT = 6;

		private Worm w;

		private int targetOffX = 0;
		private int targetOffY = 0;

		public WormPart(int x, int y, Worm w) {
			super(x, y, WIDTH, HEIGHT);
			this.w = w;
		}

		@Override
		public void tick(long delta) {
			velocity = new Vector2D(distanceX(w.getX() - targetOffX), distanceY(w.getY() - targetOffY)).normalise();
			calculatePosition();
		}

		@Override
		public void render(Graphics g) {
			g.setColor(Color.blue);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}

		public int getTargetOffX() {
			return targetOffX;
		}

		public void setTargetOffX(int targetOffX) {
			this.targetOffX = targetOffX;
		}

		public int getTargetOffY() {
			return targetOffY;
		}

		public void setTargetOffY(int targetOffY) {
			this.targetOffY = targetOffY;
		}

		@Override
		protected boolean onBulletHit(boolean isPlayer) {
			return isPlayer;
		}

		public void removeWithWorm() {
			w.remove();
		}

	}

}
