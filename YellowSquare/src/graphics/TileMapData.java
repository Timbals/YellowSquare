package graphics;

import java.io.Serializable;

public class TileMapData implements Serializable {

	private static final long serialVersionUID = 4377282524563494207L;

	public int[][] tiles;
	public int width;
	public int height;

	public TileMapData(int[][] tiles, int width, int height) {
		super();
		this.tiles = tiles;
		this.width = width;
		this.height = height;
	}

}
