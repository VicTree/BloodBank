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
	
	String background;
	String music;
	String tileSet;
	String map;
	
	
	
	public Level1State(GameStateManager gsm, String background, String tileSet, String map, String music) {
		this.gsm = gsm;
		this.background = background;
		this.tileSet = tileSet;
		this.map = map;
		this.music = music;
		init();
	}
	
	public void init(){
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/" + tileSet + ".png");
		tileMap.loadMap("/Maps/" + map + ".map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/" + background + ".png", 0.1);
		
		
		bgMusic = new AudioPlayer("/Music/" + music + ".mp3");
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












