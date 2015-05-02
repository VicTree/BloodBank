package gameState;

import main.GamePanel;
import entity.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import tileMap.*;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private AudioPlayer bgMusic;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();
		
	}
	
	
	public void update() {
		
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		
	}
	
	public void keyPressed(int k) {

	}
	
	public void keyReleased(int k) {

	}
	
}












