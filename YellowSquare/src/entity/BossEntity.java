package entity;

import graphics.animation.BloodSplatter;
import graphics.animation.GameBeaten;
import stage.Map;

public abstract class BossEntity extends Entity {

	protected int maxHealth;
	protected int health;

	public BossEntity(int x, int y, int width, int height, int maxHealth) {
		super(x, y, width, height);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public void remove() {
		super.remove();
		if (Map.difficulty / 5 == 4) {
			new GameBeaten().start();
		} else {
			new StairDown(getX(), getY());
		}
		new BloodSplatter(getX(), getY()).start();
	}

}
