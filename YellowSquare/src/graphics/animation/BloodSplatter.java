package graphics.animation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import main.GamePanel;
import maths.Vector2D;

public class BloodSplatter extends Animation {

	private static final int amountSplat = 20;
	private static final Random r = new Random();

	private Sound sfx_splat;

	private List<Splat> splatters = new ArrayList<Splat>();

	public BloodSplatter(int x, int y) {
		super();

		for (int i = 0; i < amountSplat; i++) {
			int size = r.nextInt(2) + 1;
			Vector2D vel = new Vector2D((r.nextInt(800) - 400) / 100, (r.nextInt(800) - 400) / 100);
			splatters.add(new Splat(x, y, size, vel));
		}

		try {
			sfx_splat = new Sound(GamePanel.resfolder + "sound" + File.separator + "die.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		sfx_splat.play(1f, GamePanel.SFX_VOLUME);
	}

	@Override
	protected void tick() {
		for (int i = 0; i < splatters.size(); i++) {
			splatters.get(i).tick();
		}
	}

	@Override
	protected void render(Graphics g) {
		for (int i = 0; i < splatters.size(); i++) {
			splatters.get(i).render(g);
		}
	}

	class Splat {

		private double x;
		private double y;
		private int size;
		private Vector2D velocity;

		public Splat(int x, int y, int size, Vector2D velocity) {
			super();
			this.x = x;
			this.y = y;
			this.size = size;
			this.velocity = velocity;
		}

		public void tick() {
			x += velocity.getX();
			y += velocity.getY();

			velocity.setX(velocity.getX() * 0.8);
			velocity.setY(velocity.getY() * 0.8);

			if (velocity.getX() < 0.1 && velocity.getX() > -0.1)
				velocity.setX(0);

			if (velocity.getY() < 0.1 && velocity.getY() > -0.1)
				velocity.setY(0);
		}

		public void render(Graphics g) {
			g.setColor(Color.red);
			g.fillRect((int) x, (int) y, size, size);
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public Vector2D getVelocity() {
			return velocity;
		}

		public void setVelocity(Vector2D velocity) {
			this.velocity = velocity;
		}

	}

}
