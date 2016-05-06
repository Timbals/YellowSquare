package stage;

import java.util.Random;

import entity.ClueTower;
import entity.Player;
import entity.Searcher;
import entity.Tower;
import entity.Worm;
import main.GamePanel;

public class EnemySpawner {

	private static final int maxDistance = 60;

	private static Random r = new Random();

	private int amountTower;
	private int amountSearcher;
	private int amountClueTower;
	private int amountWorm;

	public EnemySpawner(int amountTower, int amountSearcher, int amountClueTower, int amountWorm) {
		super();
		this.amountTower = amountTower;
		this.amountSearcher = amountSearcher;
		this.amountClueTower = amountClueTower;
		this.amountWorm = amountWorm;
	}

	public void spawn() {
		for (int i = 0; i < amountSearcher; i++) {
			Searcher s = new Searcher(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			while (Math.sqrt(Math.pow(s.getY() - Player.instance.getY(), 2)
					+ Math.pow(s.getX() - Player.instance.getX(), 2)) < maxDistance) {
				s.remove();
				s = new Searcher(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			}
		}
		for (int i = 0; i < amountTower; i++) {
			Tower t = new Tower(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			while (Math.sqrt(Math.pow(t.getY() - Player.instance.getY(), 2)
					+ Math.pow(t.getX() - Player.instance.getX(), 2)) < maxDistance) {
				t.remove();
				t = new Tower(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			}
		}
		for (int i = 0; i < amountClueTower; i++) {
			ClueTower c = new ClueTower(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			while (Math.sqrt(Math.pow(c.getY() - Player.instance.getY(), 2)
					+ Math.pow(c.getX() - Player.instance.getX(), 2)) < maxDistance) {
				c.remove();
				c = new ClueTower(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			}
		}
		for (int i = 0; i < amountWorm; i++) {
			Worm w = new Worm(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			while (Math.sqrt(Math.pow(w.getY() - Player.instance.getY(), 2)
					+ Math.pow(w.getX() - Player.instance.getX(), 2)) < maxDistance) {
				w.remove();
				w = new Worm(r.nextInt(GamePanel.WIDTH - 42) + 21, r.nextInt(GamePanel.HEIGHT - 42) + 21);
			}
		}
	}

}
