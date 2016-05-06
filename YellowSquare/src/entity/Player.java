package entity;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import entity.SearcherBoss.SearcherBossPart;
import entity.Worm.WormPart;
import graphics.Sprite;
import graphics.animation.BloodSplatter;
import main.GamePanel;
import maths.Vector2D;

public class Player extends Entity {

	public static Player instance;

	private ArrayList<Class<?>> deadly = new ArrayList<Class<?>>();

	public boolean right = false;
	public boolean left = false;
	public boolean up = false;
	public boolean down = false;

	private double speed = 1.5;

	public int shootSpeed = 90;
	private int lastShot = shootSpeed;

	public int bulletSize = 2;

	private Sound sfx_shoot;
	private Sound sfx_hit;

	public int maxHealth = 5;
	public int health = 3;
	private Animation heartBreak;

	private boolean reverseX = false;
	private boolean reverseY = false;
	private int reverseXTimer = 0;
	private int reverseYTimer = 0;

	public Player(int x, int y) {
		super(x, y, 8, 8);

		instance = this;

		try {
			sfx_shoot = new Sound(GamePanel.resfolder + "sound" + File.separator + "shoot.wav");
			sfx_hit = new Sound(GamePanel.resfolder + "sound" + File.separator + "hit.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		heartBreak = Sprite.getAnimation(Sprite.HEARTBREAK, 180);
		heartBreak.setLooping(false);
		heartBreak.stop();
		heartBreak.setCurrentFrame(heartBreak.getFrameCount() - 1);

		deadly.add(Searcher.class);
		deadly.add(Worm.class);
		deadly.add(WormPart.class);
		deadly.add(SearcherBoss.class);
		deadly.add(SearcherBossPart.class);
		deadly.add(Tower.class);
		deadly.add(TowerBoss.class);
		deadly.add(ClueTower.class);
	}

	@Override
	public void tick(long delta) {
		heartBreak.update(delta);

		if (health <= 0) {
			kill();
		}

		if (shootSpeed < 10)
			shootSpeed = 10;

		lastShot--;
		if (lastShot <= 0) {
			if (in.isMouseButtonDown(0) || GamePanel.xSecondAxis > 0.75 || GamePanel.xSecondAxis < -0.75
					|| GamePanel.ySecondAxis > 0.75 || GamePanel.ySecondAxis < -0.75) {
				PlayerBullet pb = new PlayerBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, bulletSize);
				pb.setX((int) (pb.getX() + pb.getVelocity().getX()));
				pb.setY((int) (pb.getY() + pb.getVelocity().getY()));
				lastShot = shootSpeed;

				sfx_shoot.play(1f, GamePanel.SFX_VOLUME);
			}
		}

		reverseXTimer--;
		reverseYTimer--;
		if (reverseXTimer <= 0)
			reverseX = false;
		if (reverseYTimer <= 0)
			reverseY = false;

		if (in.isKeyDown(Input.KEY_D) || GamePanel.xAxis > 0.5) {
			if (!reverseX)
				velocity.setX(speed);
		} else if (in.isKeyDown(Input.KEY_A) || GamePanel.xAxis < -0.5) {
			if (!reverseX)
				velocity.setX(-speed);
		} else {
			velocity.setX(0);
			reverseX = false;
		}

		if (in.isKeyDown(Input.KEY_W) || GamePanel.yAxis < -0.5) {
			if (!reverseY)
				velocity = velocity.add(new Vector2D(0, -speed).normalise().multiply(0.6)).normalise().multiply(speed);
		} else if (in.isKeyDown(Input.KEY_S) || GamePanel.yAxis > 0.5) {
			if (!reverseY)
				velocity = velocity.add(new Vector2D(0, speed).normalise().multiply(0.6)).normalise().multiply(speed);
		} else {
			velocity.setY(0);
			reverseY = false;
		}

		calculatePosition();

		for (int i = 0; i < Entity.getEntities().size(); i++) {
			Entity e = Entity.getEntities().get(i);
			if (deadly.contains(e.getClass())) {
				if (e.getHitbox().intersects(getHitbox())) {
					subtractHealth();
					if (e instanceof BossEntity) {
						((BossEntity) e).health--;
						setX((int) (x - velocity.getX()));
						setY((int) (y - velocity.getY()));
						velocity = velocity.normalise().multiply(-speed);
						reverseX = true;
						reverseY = true;
						reverseXTimer = 10;
						reverseYTimer = 10;
					} else if (e instanceof WormPart) {
						((WormPart) e).removeWithWorm();
					} else {
						e.remove();
					}
					return;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(getX(), getY(), width, height);
		Image heart = Sprite.get("heart.png");
		for (int i = 0; i < health; i++) {
			g.drawImage(heart, 2 + i * 10, 1);
			if (i == health - 1 && health < maxHealth) {
				heartBreak.draw(2 + (i + 1) * 10, 1);
			}
		}
	}

	public void subtractHealth() {
		health--;
		sfx_hit.play(1f, GamePanel.SFX_VOLUME);
		heartBreak.restart();
	}

	public void kill() {
		remove();
		new BloodSplatter(getX() + width / 2, getY() + height / 2).start();
	}

	protected boolean onBulletHit(boolean isPlayer) {
		if (!isPlayer) {
			subtractHealth();
			b.remove();
		}
		return false;
	}

}
