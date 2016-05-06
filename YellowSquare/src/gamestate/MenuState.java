package gamestate;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import entity.Entity;
import graphics.Sprite;
import main.GamePanel;

public class MenuState extends GameState {

	private int selected;
	private int amountOptions;

	private Sound sfx_select;

	private Image image;

	private int lastChange = 10;

	private Rectangle play;
	private Rectangle quit;

	@Override
	public void init() {
		selected = 0;
		amountOptions = 2;

		image = Sprite.get("menu.png");

		try {
			sfx_select = new Sound(GamePanel.resfolder + "sound" + File.separator + "select.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		Entity.getEntities().clear();

		play = new Rectangle(40, 50, 50, 24);
		quit = new Rectangle(40, 86, 50, 24);
	}

	@Override
	public void tick() {
		lastChange--;
		if (in.isKeyPressed(Input.KEY_W) || (GamePanel.yAxis < -0.5 && lastChange <= 0)) {
			selected--;
			lastChange = 10;
			sfx_select.play(1f, GamePanel.SFX_VOLUME);
		} else if (in.isKeyPressed(Input.KEY_S) || (GamePanel.yAxis > 0.5 && lastChange <= 0)) {
			selected++;
			lastChange = 10;
			sfx_select.play(1f, GamePanel.SFX_VOLUME);
		}
		if (selected >= amountOptions)
			selected = 0;
		if (selected < 0)
			selected = amountOptions - 1;

		if (in.isKeyPressed(Input.KEY_ENTER) || in.isButton1Pressed(Input.ANY_CONTROLLER)) {
			enter();
		}

		if (GamePanel.in.isMouseButtonDown(0)) {
			Rectangle mouse = new Rectangle(GamePanel.in.getMouseX(), GamePanel.in.getMouseY(), 1, 1);
			if (play.intersects(mouse)) {
				selected = 0;
				enter();
			} else if (quit.intersects(mouse)) {
				selected = 1;
				enter();
			}
		}
	}

	private void enter() {
		if (selected == 0) {
			GameStateManager.instance.loadState(GameStateManager.SINGLEPLAYERSTATE);
		} else if (selected == 1) {
			System.exit(0);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.white);
		/*
		 * TrueTypeFont font = new TrueTypeFont(new Font("Fixedsys", 0, 12),
		 * false); g.setFont(font); font.drawString(GamePanel.WIDTH / 2 -
		 * g.getFont().getWidth("GPPCC 7 - Noname") / 2, 30, "GPPCC 7 - Noname"
		 * );
		 * 
		 * renderOption(g, "Play", 0); renderOption(g, "Credits", 1);
		 * renderOption(g, "Quit", 2);
		 */

		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g.drawImage(image, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, 0, 0, image.getWidth(), image.getHeight());

		g.setColor(Color.black);
		g.fillRect(25, 58 + selected * 36, 8, 8);
	}

}
