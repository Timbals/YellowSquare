package entity;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import graphics.Sprite;
import main.GamePanel;
import maths.Vector2D;

public class Tower extends Entity {

	private static final Random r = new Random();

	private int shotInterval = 60 * 4;
	private int nextShot = shotInterval;
	private boolean shot = false;
	protected boolean clue = false;

	protected Animation a;
	private Sound sfx_shoot;

	public Tower(int x, int y) {
		super(x, y, 8, 8);

		a = Sprite.getAnimation(Sprite.TOWER, 300);
		a.setAutoUpdate(true);
		a.setLooping(false);

		try {
			sfx_shoot = new Sound(GamePanel.resfolder + "sound" + File.separator + "shoot.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick(long delta) {
		nextShot--;
		a.update(delta);
		if (nextShot <= 0) {
			a.restart();

			nextShot = Integer.MAX_VALUE;

			shot = false;
		}

		if (a.getFrame() == a.getFrameCount() - 1 && !shot) {
			if (clue) {
				new ClueBullet(getX() + width / 2 - 1, getY() + height / 2 - 1,
						new Vector2D(Player.instance.getX() - x, Player.instance.getY() - y));
			} else {
				new Bullet(getX() + width / 2 - 1, getY() + height / 2 - 1,
						new Vector2D(Player.instance.getX() - x, Player.instance.getY() - y));
			}

			shot = true;

			sfx_shoot.play(1f, GamePanel.SFX_VOLUME);

			nextShot = r.nextInt(shotInterval / 2) + shotInterval / 4;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(a.getCurrentFrame(), getX(), getY(), getX() + width, getY() + height, 0, 0,
				a.getCurrentFrame().getWidth(), a.getCurrentFrame().getHeight());
	}

	@Override
	protected boolean onBulletHit(boolean isPlayer) {
		return isPlayer;
	}

}
