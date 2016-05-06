package entity;

import graphics.Sprite;

public class ClueTower extends Tower {

	public ClueTower(int x, int y) {
		super(x, y);
		clue = true;
		a = Sprite.getAnimation(Sprite.CLUETOWER, 300);
		a.setAutoUpdate(true);
		a.setLooping(false);
	}

}
