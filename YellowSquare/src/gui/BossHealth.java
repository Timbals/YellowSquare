package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import entity.BossEntity;
import main.GamePanel;
import stage.Stage;

public class BossHealth {

	public static void render(Graphics g) {
		try {
			BossEntity boss = Stage.getBossEntity();

			g.setColor(Color.red);
			g.fillRect(0, GamePanel.HEIGHT - GamePanel.HEIGHT / 16 + 1,
					(float) (GamePanel.WIDTH * ((double) boss.getHealth() / (double) boss.getMaxHealth())),
					GamePanel.HEIGHT / 16 + 1);
		} catch (Exception e) {
			System.err.println("Couldn't find boss entity");
		}
	}

}
