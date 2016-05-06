package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class GameState {

	public Input in;

	public abstract void init();

	public abstract void tick();

	public abstract void render(GameContainer gc, Graphics g);

}
