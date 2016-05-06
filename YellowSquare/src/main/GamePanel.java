package main;

import java.awt.event.MouseEvent;
import java.io.File;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import editor.Editor;
import entity.Entity;
import entity.Player;
import entity.PlayerBullet;
import gamestate.GameStateManager;
import graphics.animation.Animation;
import screenshot.ScreenshotTaker;

public class GamePanel {

	public static GamePanel instance;

	public static final int WIDTH = 128;
	public static final int HEIGHT = 128;
	public static int SCALE = 64;
	public static Graphics g;

	// public static final String resfolder = "res" + File.separator;
	public static final String resfolder = "";

	public static int offx = 0;
	public static int offy = 0;

	public static int fps = 0;
	public static int tps = 0;

	public static final Color defaultColor = new Color(255, 255, 255);
	public static UnicodeFont defaultFont = null;

	public static float SFX_VOLUME = 0.4f;
	public static float MUSIC_VOLUME = 1.0f;

	public static Input in;

	public static boolean paused = false;

	private GameStateManager gsm;

	private Sound sfx_shoot;

	// controller input variables
	public static float xAxis = 0;
	public static float yAxis = 0;
	public static float xSecondAxis = 0;
	public static float ySecondAxis = 0;
	private boolean xChange = false;
	private boolean yChange = false;
	private boolean xSecondChange = false;
	private boolean ySecondChange = false;

	public GamePanel() {
		super();
		instance = this;

		// setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		// setFocusable(true);
		// requestFocus();
	}

	/*
	 * public void run() { init();
	 * 
	 * long lastTime = System.nanoTime(); double ns = 1000000000 / max_ticks;
	 * double nsframes = 1000000000 / max_fps; double delta = 0; double
	 * deltaframes = 0;
	 * 
	 * long timer = System.currentTimeMillis();
	 * 
	 * int ticks = 0; int frames = 0; while (running) { long now =
	 * System.nanoTime(); delta += (now - lastTime) / ns; deltaframes += (now -
	 * lastTime) / nsframes; lastTime = now;
	 * 
	 * while (delta >= 1) { tick(); ticks++; delta--; }
	 * 
	 * if (deltaframes >= 1 || max_fps == 1000) { render(); drawToScreen();
	 * frames++; deltaframes--; }
	 * 
	 * if (System.currentTimeMillis() - timer > 1000) { timer += 1000; fps =
	 * frames; tps = ticks; ticks = 0; frames = 0;
	 * 
	 * Main.frame.setTitle(Main.TITLE + "        " + tps + " tps | " + fps +
	 * " fps"); } }
	 * 
	 * try { thread.join(); } catch (InterruptedException e) {
	 * e.printStackTrace(); } }
	 * 
	 * @Override public void addNotify() { super.addNotify(); if (thread ==
	 * null) { thread = new Thread(this); addKeyListener(this);
	 * addMouseListener(this); thread.start(); } }
	 */

	@SuppressWarnings("unchecked")
	public void init() {
		// createBufferStrategy(3);

		// g = (Graphics2D) image.getGraphics();

		gsm = new GameStateManager();

		try {
			sfx_shoot = new Sound(resfolder + "sound" + File.separator + "shoot.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		try {
			defaultFont = new UnicodeFont(GamePanel.resfolder + "fonts" + File.separator + "pixelberry.TTF", 8, false,
					false);
			defaultFont.addAsciiGlyphs();
			defaultFont.addGlyphs(400, 600);
			defaultFont.getEffects().add(new ColorEffect());
			defaultFont.loadGlyphs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tick(GameContainer gc, long delta) {
		for (int i = 0; i < Controllers.getControllerCount(); i++) {
			if (Controllers.getController(i).getName().startsWith("Controller")) {
				xAxis = Controllers.getController(i).getXAxisValue();
				yAxis = Controllers.getController(i).getYAxisValue();
				xSecondAxis = Controllers.getController(i).getAxisValue(3);
				ySecondAxis = Controllers.getController(i).getAxisValue(2);
				if (xAxis != -1)
					xChange = true;
				if (yAxis != -1)
					yChange = true;
				if (xSecondAxis != -1)
					xSecondChange = true;
				if (ySecondAxis != -1)
					ySecondChange = true;
				if (xAxis == -1 && !xChange)
					xAxis = 0;
				if (yAxis == -1 && !yChange)
					yAxis = 0;
				if (xSecondAxis == -1 && !xSecondChange)
					xSecondAxis = 0;
				if (ySecondAxis == -1 && !ySecondChange)
					ySecondAxis = 0;
			}
		}

		ScreenshotTaker.tick(gc);

		if (paused)
			return;

		Entity.staticTick(delta);

		Animation.staticTick();

		gsm.tick();

		Editor.tick();
	}

	public void render(GameContainer gc, Graphics g) {
		GamePanel.g = g;

		g.setColor(defaultColor);
		g.setFont(defaultFont);

		gsm.render(gc, g);

		// Shadows.calc(gc, g);

		Editor.render(g);
	}

	/*
	 * private void drawToScreen() { Graphics g2 = getGraphics();
	 * g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	 * g2.dispose(); }
	 */

	public void mouseClicked(MouseEvent event) {

	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	public void mousePressed(MouseEvent event) {
		if (Entity.getEntities().contains(Player.instance)) {
			try {
				PlayerBullet b = new PlayerBullet(Player.instance.getX() + Player.instance.getWidth() / 2,
						Player.instance.getY() + Player.instance.getHeight() / 2);
				// spawning bullets outside of player = better responsiveness
				b.setX((int) (b.getX() + b.getVelocity().getX()));
				b.setY((int) (b.getY() + b.getVelocity().getY()));

				sfx_shoot.play(1f, GamePanel.SFX_VOLUME);
			} catch (Exception e) {
				// when in menu
			}
		}
	}

	public void mouseReleased(MouseEvent event) {
	}

}
