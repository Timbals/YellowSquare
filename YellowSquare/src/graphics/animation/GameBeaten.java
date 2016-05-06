package graphics.animation;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import gamestate.SinglePlayerState;
import main.GamePanel;

public class GameBeaten extends Animation {

	private int timer;
	private int y = -128;

	public GameBeaten() {
		this.timer = SinglePlayerState.timer;
	}

	@Override
	protected void tick() {
		y = tick - 128;
		if (y > 0)
			y = 0;
	}

	@Override
	protected void render(Graphics g) {
		g.setColor(Color.yellow);

		String gb = "Game Beaten!";
		g.drawString(gb, GamePanel.WIDTH / 2 - g.getFont().getWidth(gb) / 2, y + 40);
		String time = timer / 60 + " seconds";
		g.drawString(time, GamePanel.WIDTH / 2 - g.getFont().getWidth(time) / 2, y + 80);
	}

}
