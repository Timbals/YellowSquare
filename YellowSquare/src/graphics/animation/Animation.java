package graphics.animation;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public abstract class Animation {

	protected int tick = 0;
	protected boolean running = false;
	protected boolean loop = false;

	public Animation() {
		animations.add(this);
	}

	protected abstract void tick();

	protected abstract void render(Graphics g);

	public void start() {
		running = true;
	}

	public void pause() {
		running = false;
	}

	public void stop() {
		running = false;
		tick = 0;
	}

	// static

	private static ArrayList<Animation> animations = new ArrayList<Animation>();

	public static void staticTick() {
		for (int i = animations.size() - 1; i >= 0; i--) {
			try {
				if (animations.get(i).running) {
					animations.get(i).tick();
					animations.get(i).tick++;
				}
			} catch (Exception e) {
				System.err.println("error updating animation");
			}
		}
	}

	public static void staticRender(Graphics g) {
		for (int i = 0; i < animations.size(); i++) {
			if (animations.get(i).running) {
				animations.get(i).render(g);
			}
		}
	}

	public static void removeAll() {
		while (!animations.isEmpty())
			animations.remove(0);
	}

}
