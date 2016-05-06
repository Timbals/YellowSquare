package entity;

import maths.Vector2D;

public class PlayerBullet extends Bullet {

	public PlayerBullet(int x, int y, Vector2D velocity) {
		super(x, y, velocity);

	}

	public PlayerBullet(int x, int y) {
		super(x, y);
	}

	public PlayerBullet(int x, int y, int size) {
		super(x, y, size);
	}

}
