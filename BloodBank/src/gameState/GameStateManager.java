package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameStateManager extends GameStateManagerAb{
	
	//public static final int NUMGAMESTATES = 2;
	public static final int MENU = 1;
	public final static int LEVELSELECTOR =2;
	public final static int LEVEL1 = 3;
	public final static int LEVEL2 = 4;
	public final static int LEVEL3 = 5;
	
	String[] options = {"Start", "Help", "Quit"};
	
	public GameStateManager(){
		super();
		   
	}
 
	@Override
	public void loadState(int state) {
		/*if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		*/
		
		switch (state){
		case 1: gameStates[state] = new MenuState(this, "background", "Title", options, Color.gray, Color.RED, Color.blue, new Font("Arial",Font.PLAIN, 20), new Font("Calibri",Font.PLAIN, 12));
				break;
		case 2: gameStates[state] = new LevelSelectState(this, "background", "Title", options, Color.gray, Color.RED, Color.blue, new Font("Arial",Font.PLAIN, 20), new Font("Calibri",Font.PLAIN, 12));
				break;
		case 3: 
		}
	}
	
	
	

}
