package stage;

import java.util.Random;

import entity.BossEntity;
import entity.Bullet;
import entity.Chest;
import entity.Entity;
import entity.SearcherBoss;
import entity.TowerBoss;
import graphics.TileMap;
import graphics.animation.Animation;
import main.GamePanel;

public class Stage {

	private static final Random r = new Random();

	public static int current = 0;

	public static final int NEWSTAGE = 0;
	public static final int SUCCESSSTAGE = 1;
	public static final int TRANSITIONSSTAGE = 2;

	private static final double chestChance = 0.4;

	private static Chest chest;

	private static EnemySpawner[] spawners;

	public static void newStage() {
		current = NEWSTAGE;

		TileMap.instance.genRoom(true);
	}

	public static void successStage() {
		current = SUCCESSSTAGE;

		TileMap.instance.genRoom();

		if (r.nextInt((int) (100)) + 1 <= chestChance * 100) {
			chest = new Chest(GamePanel.WIDTH / 2 - 4, GamePanel.HEIGHT / 2 - 4);
			Map.setChest(true);
		}

		Map.setType(Map.CLEARED);
	}

	public static void transitionStage() {
		current = TRANSITIONSSTAGE;

		Map.discover();

		TileMap.instance.genRoom();

		if (chest != null) {
			chest.remove();
			chest = null;
		}

		if (Map.isChest()) {
			chest = new Chest(GamePanel.WIDTH / 2 - 4, GamePanel.HEIGHT / 2 - 4);
		}

		for (int i = 0; i < Entity.getEntities().size(); i++) {
			Entity e = Entity.getEntities().get(i);
			if (e instanceof Bullet) {
				e.remove();
				i--;
			}
		}
		Animation.removeAll();
	}

	public static void spawnEnemies() {
		spawnEnemies(Map.difficulty);
	}

	public static void spawnEnemies(int difficulty) {
		if (spawners == null) {
			spawners = new EnemySpawner[11];

			spawners[0] = new EnemySpawner(0, 1, 0, 0);
			spawners[1] = new EnemySpawner(1, 0, 0, 0);
			spawners[2] = new EnemySpawner(1, 1, 0, 0);
			spawners[3] = new EnemySpawner(0, 2, 0, 0);
			spawners[4] = new EnemySpawner(2, 0, 0, 0);
			spawners[5] = new EnemySpawner(0, 0, 1, 0);
			spawners[6] = new EnemySpawner(0, 2, 1, 1);
			spawners[7] = new EnemySpawner(1, 2, 1, 0);
			spawners[8] = new EnemySpawner(0, 2, 2, 1);
			spawners[9] = new EnemySpawner(0, 1, 4, 0);
			spawners[10] = new EnemySpawner(1, 1, 1, 2);
		}

		difficulty /= 5;
		if (Map.isBossRoom()) {
			if (r.nextBoolean()) {
				boss = new TowerBoss(GamePanel.WIDTH / 2 - 8, GamePanel.HEIGHT / 2 - 8);
			} else {
				boss = new SearcherBoss(GamePanel.WIDTH / 2 - 8, GamePanel.HEIGHT / 2 - 8);
			}
		} else {
			spawners[r.nextInt(5) + (difficulty - 1) * 2].spawn();
		}
	}

	private static BossEntity boss;

	public static BossEntity getBossEntity() {
		return boss;
	}

}
