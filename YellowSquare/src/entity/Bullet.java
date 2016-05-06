package entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import main.GamePanel;
import maths.Vector2D;

public class Bullet extends Entity {

	protected double speed = 2;

	private boolean xNull;
	private boolean yNull;

	public Bullet(int x, int y) {
		super(x, y, 2, 2);
		velocity = new Vector2D(in.getMouseX() - x, in.getMouseY() - y).normalise().multiply(speed);
		if (GamePanel.xSecondAxis > 0.75 || GamePanel.xSecondAxis < -0.75 || GamePanel.ySecondAxis > 0.75
				|| GamePanel.ySecondAxis < -0.75)
			velocity = new Vector2D(GamePanel.xSecondAxis, GamePanel.ySecondAxis).normalise().multiply(speed);
		init();
	}

	public Bullet(int x, int y, int size) {
		super(x, y, 2, 2);
		velocity = new Vector2D(in.getMouseX() - x, in.getMouseY() - y).normalise().multiply(speed);
		if (GamePanel.xSecondAxis > 0.75 || GamePanel.xSecondAxis < -0.75 || GamePanel.ySecondAxis > 0.75
				|| GamePanel.ySecondAxis < -0.75)
			velocity = new Vector2D(GamePanel.xSecondAxis, GamePanel.ySecondAxis).normalise().multiply(speed);
		this.width = size;
		this.height = size;
		init();
	}

	public Bullet(int x, int y, Vector2D velocity) {
		super(x, y, 2, 2);
		this.velocity = velocity.normalise().multiply(speed);
		init();
	}

	public Bullet(int x, int y, Vector2D velocity, double speed) {
		super(x, y, 2, 2);
		this.speed = speed;
		this.velocity = velocity.normalise().multiply(speed);
		init();
	}

	public Bullet(int x, int y, Vector2D velocity, double speed, int size) {
		super(x, y, 2, 2);
		this.speed = speed;
		this.velocity = velocity.normalise().multiply(speed);
		this.width = size;
		this.height = size;
		init();
	}

	private void init() {
		if (velocity.getX() == 0)
			xNull = true;
		if (velocity.getY() == 0)
			yNull = true;
	}

	@Override
	public void tick(long delta) {
		calculatePosition();

		if ((velocity.getX() == 0 && !xNull) || (velocity.getY() == 0 && !yNull)) {
			remove();
		}

		if (x < 0 || x > GamePanel.WIDTH || y < 0 || y > GamePanel.HEIGHT)
			remove();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(getX(), getY(), width, height);
	}

}
