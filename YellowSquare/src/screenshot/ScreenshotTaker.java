package screenshot;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.imageout.ImageOut;

import main.GamePanel;

public class ScreenshotTaker {

	private static boolean enabled = false;

	private static Image screenBuffer;

	public static void tick(GameContainer gc) {
		if (GamePanel.in.isKeyPressed(Input.KEY_SPACE) && enabled) {
			takeScreenshot(gc);
		}
	}

	public static void takeScreenshot(GameContainer container) {
		try {
			File FileSSDir = new File("screenshots");
			if (!FileSSDir.exists()) {
				FileSSDir.mkdirs();
			}

			int number = 0;
			String screenShotFileName = "C:" + File.separator + "screenshotsgppcc7" + File.separator + number + ".png";
			File screenShot = new File(screenShotFileName);

			while (screenShot.exists()) {
				number++;
				screenShotFileName = "C:" + File.separator + "screenshotsgppcc7" + File.separator + number + ".png";
				screenShot = new File(screenShotFileName);
			}

			screenBuffer = new Image(container.getWidth(), container.getHeight());

			Graphics g = container.getGraphics();
			g.copyArea(screenBuffer, 0, 0);
			ImageOut.write(screenBuffer, screenShotFileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
