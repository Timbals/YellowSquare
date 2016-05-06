package entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import main.GamePanel;
import maths.Vector2D;

public class TowerBoss extends BossEntity {

	private int maxNextWave = 60 * 2;
	private int nextWave = maxNextWave;

	private int maxNextSpawn = 60 * 6;
	private int nextSpawn = maxNextSpawn;

	private BulletWave wave;

	public TowerBoss(int x, int y) {
		super(x, y, 16, 16, 20);
	}

	@Override
	public void tick(long delta) {
		if (wave != null) {
			wave.tick();
			if (wave.isFinnished())
				nextWave--;
		} else {
			nextWave--;
		}

		if (nextWave <= 0) {
			nextWave = maxNextWave;

			wave = new BulletWave(getX(), getY(), 5);
		}

		boolean noTower = true;
		for (Entity e : Entity.getEntities()) {
			if (e instanceof Tower)
				noTower = false;
		}

		if (noTower) {
			nextSpawn--;
			if (nextSpawn <= 0) {
				nextSpawn = maxNextSpawn;

				Tower destroy = new Tower(GamePanel.WIDTH - 30, 22);
				Tower two = new Tower(22, GamePanel.HEIGHT - 30);
				Tower three = new Tower(GamePanel.WIDTH - 30, GamePanel.HEIGHT - 30);

				if (two.distance(Player.instance) < destroy.distance(Player.instance))
					destroy = two;

				if (three.distance(Player.instance) < destroy.distance(Player.instance))
					destroy = three;

				destroy.remove();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	protected boolean onBulletHit(boolean isPlayer) {
		if (isPlayer) {
			health--;
			b.remove();
		}
		if (health <= 0) {
			remove();
		}
		return false;
	}

	class BulletWave {

		private int shots = 0;
		private int amountShots;
		private int maxNextShot = 5;
		private int nextShot = maxNextShot;

		public BulletWave(int x, int y, int amountShots) {
			this.amountShots = amountShots;
		}

		private void tick() {
			nextShot--;

			if (nextShot <= 0 && shots < amountShots) {
				nextShot = maxNextShot;

				shots++;

				Bullet b = new Bullet(getX() + width / 2 - 1, getY() + height / 2 - 1,
						new Vector2D(Player.instance.getX() - x, Player.instance.getY() - y), 0.8);
				b.setX((int) (b.getX() + b.getVelocity().getX()));
				b.setY((int) (b.getY() + b.getVelocity().getY()));
			}
		}

		private boolean isFinnished() {
			return shots >= amountShots;
		}

	}

}
