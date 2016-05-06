package graphics;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import main.GamePanel;

public class Sprite {

	private static final String path = GamePanel.resfolder + "sprites" + File.separator;

	private static HashMap<String, Image> images = new HashMap<String, Image>();

	public static Image get(String name) {
		try {
			if (images.get(name) == null)
				images.put(name, new Image(path + name, false, Image.FILTER_NEAREST));
		} catch (Exception e) {
			return null;
		}
		return images.get(name);
	}

	public static final int TOWER = 0;
	public static final int CLUETOWER = 1;
	public static final int HEARTBREAK = 2;

	private static HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();

	public static Animation getAnimation(int id, int duration) {
		try {
			Image[] frames = new Image[0];

			if (id == TOWER) {
				frames = new Image[6];
				frames[0] = new Image(path + "tower_1.png", false, Image.FILTER_NEAREST);
				frames[1] = new Image(path + "tower_2.png", false, Image.FILTER_NEAREST);
				frames[2] = new Image(path + "tower_3.png", false, Image.FILTER_NEAREST);
				frames[3] = new Image(path + "tower_4.png", false, Image.FILTER_NEAREST);
				frames[4] = new Image(path + "tower_4.png", false, Image.FILTER_NEAREST);
				frames[5] = new Image(path + "tower_1.png", false, Image.FILTER_NEAREST);
			} else if (id == CLUETOWER) {
				frames = new Image[6];
				frames[0] = new Image(path + "cluetower_1.png", false, Image.FILTER_NEAREST);
				frames[1] = new Image(path + "cluetower_2.png", false, Image.FILTER_NEAREST);
				frames[2] = new Image(path + "cluetower_3.png", false, Image.FILTER_NEAREST);
				frames[3] = new Image(path + "cluetower_4.png", false, Image.FILTER_NEAREST);
				frames[4] = new Image(path + "cluetower_4.png", false, Image.FILTER_NEAREST);
				frames[5] = new Image(path + "cluetower_1.png", false, Image.FILTER_NEAREST);
			} else if (id == HEARTBREAK) {
				frames = new Image[6];
				frames[0] = new Image(path + "heart.png", false, Image.FILTER_NEAREST);
				frames[1] = new Image(path + "heart2.png", false, Image.FILTER_NEAREST);
				frames[2] = new Image(path + "heart3.png", false, Image.FILTER_NEAREST);
				frames[3] = new Image(path + "heart4.png", false, Image.FILTER_NEAREST);
				frames[4] = new Image(path + "heart5.png", false, Image.FILTER_NEAREST);
				frames[5] = new Image(frames[4].getWidth(), frames[4].getHeight());
			}

			animations.put(id, new Animation(frames, duration));
		} catch (Exception e) {
			return null;
		}

		return animations.get(id);
	}

}
