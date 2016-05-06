package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import main.GamePanel;

public class GameStateManager {

	public static GameStateManager instance;

	public static final int MENUSTATE = 0;
	public static final int SINGLEPLAYERSTATE = 1;

	public static final int AMOUNTSTATES = 2;

	private int currentState;
	private GameState[] states;

	public GameStateManager() {
		instance = this;

		states = new GameState[AMOUNTSTATES];
		loadState(MENUSTATE);
	}

	public void loadState(int id) {
		if (states[id] == null) {
			if (id == MENUSTATE) {
				states[id] = new MenuState();
			} else if (id == SINGLEPLAYERSTATE) {
				states[id] = new SinglePlayerState();
			}
		}

		states[id].in = GamePanel.in;
		states[id].init();

		currentState = id;
	}

	public void tick() {
		states[currentState].tick();
	}

	public void render(GameContainer gc, Graphics g) {
		states[currentState].render(gc, g);
	}

}
