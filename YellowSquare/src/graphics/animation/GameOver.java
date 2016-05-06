package graphics.animation;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import gamestate.GameStateManager;
import main.GamePanel;

public class GameOver extends Animation {

	private int y = -128;
	private int lastChange = 90;

	private boolean retrySelected = true;

	private Rectangle retryButton;
	private Rectangle quitButton;

	@Override
	protected void tick() {
		lastChange--;

		y = tick - 128;
		if (y > 0)
			y = 0;

		if ((GamePanel.in.isKeyPressed(Input.KEY_A) || GamePanel.in.isKeyPressed(Input.KEY_D) || GamePanel.xAxis > 0.5
				|| GamePanel.xAxis < -0.5 || GamePanel.yAxis > 0.5 || GamePanel.yAxis < -0.5) && lastChange <= 0) {
			retrySelected = !retrySelected;
			lastChange = 10;
		}
		if (GamePanel.in.isKeyPressed(Input.KEY_ENTER) || GamePanel.in.isButton1Pressed(Input.ANY_CONTROLLER)) {
			enter();
		}
		if (tick >= 128) {
			if (GamePanel.in.isMouseButtonDown(0)) {
				Rectangle mouse = new Rectangle(GamePanel.in.getMouseX(), GamePanel.in.getMouseY(), 1, 1);
				if (retryButton.intersects(mouse)) {
					retrySelected = true;
					enter();
				} else if (quitButton.intersects(mouse)) {
					retrySelected = false;
					enter();
				}
			}
		}
	}

	private void enter() {
		if (retrySelected) {
			GameStateManager.instance.loadState(GameStateManager.SINGLEPLAYERSTATE);
		} else {
			System.exit(0);
		}
	}

	@Override
	protected void render(Graphics g) {
		g.setColor(Color.white);
		String go = "Game Over";
		g.drawString(go, GamePanel.WIDTH / 2 - g.getFont().getWidth(go) / 2, y + 40);
		String retry = "  Retry";
		String quit = "  Quit";
		String space = "       ";
		int retryX;
		int retryY;
		int quitX;
		int quitY;
		if (retrySelected) {
			retryX = GamePanel.WIDTH / 2 - g.getFont().getWidth(">>" + retry + space) + 20 - g.getFont().getWidth("  ");
			retryY = y + 80;
			quitX = GamePanel.WIDTH / 2 + g.getFont().getWidth(retry + space) - 35 - g.getFont().getWidth("  ");
			quitY = y + 80;

			g.drawString(">>" + retry, retryX, retryY);
			g.drawString(quit, quitX, quitY);

			retryButton = new Rectangle(retryX, retryY, g.getFont().getWidth(">>" + retry),
					g.getFont().getHeight(">>" + retry));
			quitButton = new Rectangle(quitX, quitY, g.getFont().getWidth(quit), g.getFont().getHeight(quit));
		} else {
			retryX = GamePanel.WIDTH / 2 - g.getFont().getWidth(retry + space) + 20 - g.getFont().getWidth("  ");
			retryY = y + 80;
			quitX = GamePanel.WIDTH / 2 + g.getFont().getWidth(retry + space) - 36 - g.getFont().getWidth(">>")
					- g.getFont().getWidth("  ");
			quitY = y + 80;

			g.drawString(retry, retryX, retryY);
			g.drawString(">>" + quit, quitX, quitY);

			retryButton = new Rectangle(retryX, retryY, g.getFont().getWidth(retry), g.getFont().getHeight(retry));
			quitButton = new Rectangle(quitX, quitY, g.getFont().getWidth(">>" + quit),
					g.getFont().getHeight(">>" + quit));
		}
	}

}
