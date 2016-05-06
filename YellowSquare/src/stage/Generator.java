package stage;

import java.awt.Point;
import java.util.Random;

import graphics.TileMap;

public class Generator {

	private static Random r = new Random(7);

	private static int bossRoomX;
	private static int bossRoomY;

	public static void generate() {
		Map.reset();

		int x = Map.x;
		int y = Map.y;

		bossRoomX = x;
		bossRoomY = y;

		new GenSnake(x, y, r.nextInt(Map.difficulty) + Map.difficulty, 10);

		TileMap.instance.genRoom();

		Map.setType(Map.CLEARED);

		Map.setBossRoom(bossRoomX, bossRoomY);

		Map.discover();
	}

	static class GenSnake {

		private static final int maxTries = 4;

		private int lifes;
		private int x;
		private int y;

		public GenSnake(int x, int y, int lifes, int splitchance) {
			this.x = x;
			this.y = y;
			this.lifes = lifes;

			gen();
		}

		private void gen() {
			lifes--;

			int tries = 0;

			while (Map.getType(x, y) != Map.NONE) {
				if (r.nextBoolean()) {
					// change x
					if (r.nextBoolean()) {
						// positive
						x++;
						if (Map.getType(x, y) == Map.UNDISCOVERED && tries < maxTries) {
							x--;
							tries++;
						}
					} else {
						// negative
						x--;
						if (Map.getType(x, y) == Map.UNDISCOVERED && tries < maxTries) {
							x++;
							tries++;
						}
					}
				} else {
					// change y
					if (r.nextBoolean()) {
						// positive
						y++;
						if (Map.getType(x, y) == Map.UNDISCOVERED && tries < maxTries) {
							y--;
							tries++;
						}
					} else {
						// negative
						y--;
						if (Map.getType(x, y) == Map.UNDISCOVERED && tries < maxTries) {
							y++;
							tries++;
						}
					}
				}
			}

			Map.setType(Map.UNDISCOVERED, x, y);

			if (new Point(x, y).distance(new Point(Map.x, Map.y)) > new Point(bossRoomX, bossRoomY)
					.distance(new Point(Map.x, Map.y))) {
				bossRoomX = x;
				bossRoomY = y;
			}

			if (lifes > 0)
				gen();
		}

	}

}
