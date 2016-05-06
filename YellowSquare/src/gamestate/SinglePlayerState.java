package gamestate;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import editor.Editor;
import entity.Bullet;
import entity.Entity;
import entity.Player;
import graphics.TileMap;
import graphics.animation.Animation;
import graphics.animation.FadeIn;
import graphics.animation.GameOver;
import gui.BossHealth;
import gui.MapRender;
import gui.TutorialOverlay;
import main.GamePanel;
import stage.Generator;
import stage.Map;
import stage.Stage;

public class SinglePlayerState extends GameState {

	private static boolean fadein = false;

	public static int timer;

	private TileMap tilemap;

	private int deadTimer;

	private Music music_game;
	private Music music_boss;

	@Override
	public void init() {
		timer = 0;

		tilemap = new TileMap();

		Entity.removeAll();
		Animation.removeAll();

		new Player(GamePanel.WIDTH / 2 - 4, GamePanel.HEIGHT / 2 - 4);

		Stage.transitionStage();

		if (!fadein) {
			new FadeIn().start();
			fadein = true;
		}

		try {
			music_game = new Music(GamePanel.resfolder + "sound" + File.separator + "game.wav");
			music_boss = new Music(GamePanel.resfolder + "sound" + File.separator + "bosstheme.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		music_game.play(1f, GamePanel.MUSIC_VOLUME);

		deadTimer = 60;

		Map.difficulty = 0;
		Generator.generate();
	}

	private GameOver gm;

	@Override
	public void tick() {
		timer++;

		tilemap.tick();

		Map.tick();

		boolean clear = true;
		boolean dead = true;
		for (Entity e : Entity.getEntities()) {
			if (!(e instanceof Player) && !(e instanceof Bullet))
				clear = false;
			if (e instanceof Player)
				dead = false;
		}

		if (dead && !Editor.enabled) {
			deadTimer--;
			if (deadTimer <= 0) {
				music_boss.stop();
				music_game.stop();
				gm = new GameOver();
				gm.start();
				deadTimer = Integer.MAX_VALUE;
			}
		}

		if (!Editor.enabled) {
			if (clear && Stage.current == Stage.NEWSTAGE)
				Stage.successStage();

			if (Player.instance.getX() > GamePanel.WIDTH) {
				Player.instance.setX(Player.instance.getX() - GamePanel.WIDTH);
				Map.x--;
				Stage.transitionStage();
			} else if (Player.instance.getX() + Player.instance.getWidth() < 0) {
				Player.instance.setX(Player.instance.getX() + GamePanel.WIDTH);
				Map.x++;
				Stage.transitionStage();
			} else if (Player.instance.getY() > GamePanel.HEIGHT) {
				Player.instance.setY(Player.instance.getY() - GamePanel.HEIGHT);
				Map.y--;
				Stage.transitionStage();
			} else if (Player.instance.getY() + Player.instance.getHeight() < 0) {
				Player.instance.setY(Player.instance.getY() + GamePanel.HEIGHT);
				Map.y++;
				Stage.transitionStage();
			}

			if (Stage.current == Stage.TRANSITIONSSTAGE) {
				Rectangle spawn = new Rectangle(20, 20, GamePanel.WIDTH - 20 * 2, GamePanel.HEIGHT - 20 * 2);
				if (spawn.intersects(Player.instance.getHitbox()) && !Map.isClear()) {
					Stage.newStage();
					Stage.spawnEnemies();
				}
			}
		}

		if (Map.isBossRoom()) {
			if (!music_boss.playing()) {
				music_game.stop();
				music_boss.play(1f, GamePanel.MUSIC_VOLUME);
			}
		} else {
			if (!music_game.playing()) {
				music_boss.stop();
				music_game.play(1f, GamePanel.MUSIC_VOLUME);
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		MapRender.render(g);

		tilemap.render(g);

		if (Map.x == Map.LEVEL_SIZE / 2 && Map.y == Map.LEVEL_SIZE / 2)
			TutorialOverlay.render(g);

		Entity.staticRender(g);

		Animation.staticRender(g);

		if (Map.isBossRoom() && Stage.current != Stage.TRANSITIONSSTAGE)
			BossHealth.render(g);

		// Shadows.calc(gc, g);
	}

}
