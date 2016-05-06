package main;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {

	public static final String TITLE = "Yellow Square";
	public static JFrame frame;
	public static int max_fps = 60;

	private GamePanel panel;

	public Main(String title) {
		super(title);

		panel = new GamePanel();
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(
					new ScalableGame(new Main(TITLE), GamePanel.WIDTH, GamePanel.HEIGHT));

			int screenWidth = app.getScreenWidth();
			int screenHeight = app.getScreenHeight();

			while (GamePanel.WIDTH * GamePanel.SCALE >= screenWidth
					|| GamePanel.HEIGHT * GamePanel.SCALE >= screenHeight) {
				GamePanel.SCALE--;
			}
			GamePanel.SCALE--;

			app.setDisplayMode(GamePanel.WIDTH * GamePanel.SCALE, GamePanel.HEIGHT * GamePanel.SCALE, false); // frame
			// resolution
			app.setTargetFrameRate(max_fps); // frame rate lock
			app.setVSync(true); // enable vsync

			app.setShowFPS(false); // show/hide fps counter

			app.start();

			return;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		panel.render(gc, g);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		GamePanel.in = gc.getInput();

		panel.init();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		panel.tick(gc, delta);
	}

}
