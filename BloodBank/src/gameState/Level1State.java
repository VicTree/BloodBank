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
	
	private ArrayList<PickUps> pickUps;
	
	private double time;
	
	String background;
	String music;
	String tileSet;
	String map;
	
	Player player1;
	Player player2;
	ArrayList<Player> players;
	
	int coinIndex;
	
	
	
	public Level1State(GameStateManager gsm, String background, String tileSet, String map, String music) {
		this.gsm = gsm;
		this.background = background;
		this.tileSet = tileSet;
		this.map = map;
		this.music = music;
		init();
	}
	
	public void init(){
		
		tileMap = new TileMap(50);
		tileMap.loadTiles(tileSet);
		tileMap.loadMap(map);
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background(background, 0.1);
		
		players = new ArrayList<Player>();
		
		player1 = new Player(tileMap, "Blue Sprite Sheet (1)");
		player1.setPosition(100, 100);
		
		player2 = new Player(tileMap, "Blue Sprite Sheet (1)");
		player2.setPosition(200,100);
		
		players.add(player1);
		players.add(player2);
		
		pickUps = new ArrayList<PickUps>();
		time = System.nanoTime();
		
		coinIndex =0;
		
		//bgMusic = new AudioPlayer("/Music/" + music + ".mp3");
		//bgMusic.play();
		
	}
	
	
	public void update() {
		
		
		player1.update();
		player1.checkPickUps(pickUps);
		player1.checkAttack(players);
		
		player2.update();
		player2.checkPickUps(pickUps);
		player2.checkAttack(players);
		
		for (PickUps p: pickUps){
			p.update();
			if(p.shouldRemove()){
				pickUps.remove(p);
				coinIndex -=1;
			}
		}
		
		if((System.nanoTime() - time)/4  > 999999999){
			time = System.nanoTime();
			pickUps.add(new PickUps(tileMap,"coinSpriteSheet"));
			
			pickUps.get(coinIndex).setPosition(Math.random() * 480, 10);
			coinIndex+=1;
		}
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		
		
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		player1.draw(g);
		player2.draw(g);
		
		for (PickUps p: pickUps){
			p.draw(g);
		}
		
		
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player1.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player1.setRight(true);
		if(k == KeyEvent.VK_UP) player1.setUp(true);
		if(k == KeyEvent.VK_DOWN) player1.setDown(true);
		if(k == KeyEvent.VK_COMMA) player1.setJumping(true);
		if(k == KeyEvent.VK_PERIOD) player1.setShooting();
		
		if(k == KeyEvent.VK_A) player2.setLeft(true);
		if(k == KeyEvent.VK_D) player2.setRight(true);
		if(k == KeyEvent.VK_W) player2.setUp(true);
		if(k == KeyEvent.VK_S) player2.setDown(true);
		if(k == KeyEvent.VK_C) player2.setJumping(true);
		if(k == KeyEvent.VK_V) player2.setShooting();

	}
	
	public void keyReleased(int k) {

		if(k == KeyEvent.VK_LEFT) player1.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player1.setRight(false);
		if(k == KeyEvent.VK_UP) player1.setUp(false);
		if(k == KeyEvent.VK_DOWN) player1.setDown(false);
		if(k == KeyEvent.VK_COMMA) player1.setJumping(false);
		
		if(k == KeyEvent.VK_A) player2.setLeft(false);
		if(k == KeyEvent.VK_D) player2.setRight(false);
		if(k == KeyEvent.VK_W) player2.setUp(false);
		if(k == KeyEvent.VK_S) player2.setDown(false);
		if(k == KeyEvent.VK_C) player2.setJumping(false);
	}
	
}












