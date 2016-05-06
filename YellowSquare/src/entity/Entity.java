package entity;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import graphics.TileMap;
import main.GamePanel;
import maths.Vector2D;

public abstract class Entity {

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected Vector2D velocity = new Vector2D(0, 0);

	protected boolean shouldTileCollide = true;
	protected boolean shouldEntityCollide = true;

	protected Input in;

	public Entity(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		in = GamePanel.in;

		entities.add(this);
		if (this instanceof Bullet)
			bullets.add((Bullet) this);
	}

	protected void calculatePosition() {
		if (velocity == null)
			velocity = new Vector2D(0, 0);

		// Vector2D original = getVelocity();
		while (velocity.getX() > width) {
			velocity = velocity.add(new Vector2D(-width, 0));
			checkCollision();
		}
		while (velocity.getX() < -width) {
			velocity = velocity.add(new Vector2D(width, 0));
			checkCollision();
		}

		while (velocity.getY() > height) {
			velocity = velocity.add(new Vector2D(-height, 0));
			checkCollision();
		}
		while (velocity.getY() < -height) {
			velocity = velocity.add(new Vector2D(height, 0));
			checkCollision();
		}

		checkCollision();

		x += velocity.getX();
		y += velocity.getY();
	}

	private void checkCollision() {
		double xDest = x + velocity.getX();
		double yDest = y + velocity.getY();

		if (checkTileMapCollision(xDest, y)) {
			if (velocity.getX() > 0) {
				velocity.setX(velocity.getX() - 1);
				if (velocity.getX() <= 0) {
					velocity.setX(0);

				} else {
					checkCollision();
					return;
				}
			} else {
				velocity.setX(velocity.getX() + 1);
				if (velocity.getX() >= 0) {
					velocity.setX(0);
				} else {
					checkCollision();
					return;
				}
			}
			velocity.setX(0);
		}
		if (checkTileMapCollision(x, yDest)) {
			if (velocity.getY() > 0) {
				velocity.setY(velocity.getY() - 0.5);
				if (velocity.getY() <= 0) {
					velocity.setY(0);
				} else {
					checkCollision();
					return;
				}
			} else {
				velocity.setY(velocity.getY() + 0.5);
				if (velocity.getY() >= 0) {
					velocity.setY(0);
				} else {
					checkCollision();
					return;
				}
			}
			velocity.setY(0);
		}
	}

	private boolean checkTileMapCollision(double x, double y) {
		if (TileMap.instance.isSolid((int) x, (int) y))
			return true;

		if (TileMap.instance.isSolid((int) x + width - 1, (int) y))
			return true;

		if (TileMap.instance.isSolid((int) x, (int) y + height - 1))
			return true;

		if (TileMap.instance.isSolid((int) x + width - 1, (int) y + height - 1))
			return true;

		return false;
	}

	public abstract void tick(long delta);

	public abstract void render(Graphics g);

	protected boolean onBulletHit(boolean isPlayer) {
		return false;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public Shape getHitbox() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public double distance(Entity e) {
		return new Point2D.Double(getX(), getY()).distance(new Point2D.Double(e.getX(), e.getY()));
	}

	public double distanceX(double d) {
		return d - getX();
	}

	public double distanceY(double d) {
		return d - getY();
	}

	public void remove() {
		entities.remove(this);
		if (this instanceof Bullet)
			bullets.remove((Bullet) this);
	}

	// static

	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	protected static Bullet b;

	public static void staticTick(long delta) {
		try {
			for (int i = entities.size() - 1; i >= 0; i--) {
				Entity e = entities.get(i);
				e.tick(delta);
				for (int j = 0; j < bullets.size(); j++) {
					b = bullets.get(j);
					if (b.getHitbox().intersects(e.getHitbox())) {
						if (e.onBulletHit(b instanceof PlayerBullet)) {
							e.remove();
							b.remove();
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void staticRender(Graphics g) {
		try {
			for (int i = entities.size() - 1; i >= 0; i--)
				entities.get(i).render(g);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void removeAll() {
		for (int i = Entity.getEntities().size() - 1; i >= 0; i--)
			entities.remove(i);
		entities.clear();
		bullets.clear();
	}

	public static List<Entity> getEntities() {
		return entities;
	}

}
