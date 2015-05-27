package gameState;

import gameState.GameState;

public abstract class GameStateManagerAb {

	protected GameState[] gameStates;
	protected int currentState;
	
	protected final int MENUSTATE = 1;
	//public static final int NUMGAMESTATES = 2;
	//public static final int LEVEL1STATE = 1;
	
	public GameStateManagerAb() {
		
		//gameStates = new GameState[NUMGAMESTATES];

		
		
		
	}
	//looks somthing like
	/*if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
			*/
	protected abstract void loadState(int state);
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
}
