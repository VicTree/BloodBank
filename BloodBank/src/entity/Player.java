package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import tileMap.TileMap;
import Audio.AudioPlayer;
import entity.Animation;
import entity.Projectile;

public class Player extends MapObject {
	
		// player stuff
		private int coins;
		private int maxCoins;
		private boolean dead;
		private boolean flinching;
		private long flinchTimer;
		
		// shooting
		private boolean shooting;
		private int shotDamage;
		private ArrayList<Projectile> coinProjectiles;
		
		// animations
		private ArrayList<BufferedImage[]> sprites;
		private final int[] numFrames = {
			//
		};
		
		// animation actions
		private static final int IDLE = 0;
		private static final int WALKING = 1;
		private static final int JUMPING = 2;
		private static final int FALLING = 3;
		private static final int SHOOTING= 5;
		private static final int MELEEING = 6;
		private static final int VICTORYDANCE = 7;
		private static final int DEATH = 8;
		private static final int DASHING = 9;
		
		private HashMap<String, AudioPlayer> sfx;
		
		
		
		public Player(TileMap tm, String key){
			super(tm);
			
			width = 22;
			height = 39;
			cwidth = 22;
			cheight = 39;
			
			moveSpeed = 0.3;
			maxSpeed = 1.6;
			stopSpeed = 0.4;
			fallSpeed = 0.15;
			maxFallSpeed = 4.0;
			jumpStart = -4.8;
			stopJumpSpeed = 0.3;
			
			facingRight = true;
			
			coins = maxCoins = 10;
			
			shotDamage = 3;
			coinProjectiles = new ArrayList<Projectile>();
			
			try {
				
				BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Players" + key + ".gif"));
				
				sprites = new ArrayList<BufferedImage[]>();
				for(int i = 0; i < numFrames.length; i++) {
					
					BufferedImage[] bi =
						new BufferedImage[numFrames[i]];
					
					for(int j = 0; j < numFrames[i]; j++) {
						
							bi[j] = spritesheet.getSubimage(
									j * width,
									i * height,
									width,
									height);	
					}
					
					sprites.add(bi);
					
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			animation = new Animation();
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(400);
			
			sfx = new HashMap<String, AudioPlayer>();
		}
		
		public int getCoins(){
			return coins;
		}
		
		public int getMaxCoins(){
			return maxCoins;
		}
		
		public void setShooting(){
			shooting = true;
		}
		
		
		
		public void checkAttack(ArrayList<Player> players){
			
			
			for(Player p: players){
				
				// coin shots
				for(int j = 0; j < coinProjectiles.size(); j++) {
					if(coinProjectiles.get(j).intersects(p)) {
						p.hit(shotDamage);
						coinProjectiles.get(j).setHit();
						break;
					}
				
				
				// check enemy collision
//				if(intersects(p)) {
//					hit(p.shotDamage);
//				}
				
			}
			}
			
		}
		
		
		
		public void hit(int damage) {
			if(flinching) return;
			coins -= damage;
			if(coins < 0) coins = 0;
			if(coins == 0) dead = true;
			flinching = true;
			flinchTimer = System.nanoTime();
		}
		
		private void getNextPosition() {
			
			// movement
			if(left) {
				dx -= moveSpeed;
				if(dx < -maxSpeed) {
					dx = -maxSpeed;
				}
			}
			else if(right) {
				dx += moveSpeed;
				if(dx > maxSpeed) {
					dx = maxSpeed;
				}
			}
			else {
				if(dx > 0) {
					dx -= stopSpeed;
					if(dx < 0) {
						dx = 0;
					}
				}
				else if(dx < 0) {
					dx += stopSpeed;
					if(dx > 0) {
						dx = 0;
					}
				}
			}
			
			if(currentAction == SHOOTING && !(jumping || falling)) {
				dx = 0;
			}
			
			// jumping
			if(jumping && !falling) {
				sfx.get("jump").play();
				dy = jumpStart;
				falling = true;
			}
			
			// falling
			if(falling) {
				
				dy += fallSpeed;
				
				if(dy > 0) jumping = false;
				if(dy < 0 && !jumping) dy += stopJumpSpeed;
				
				if(dy > maxFallSpeed) dy = maxFallSpeed;
				
			}
			
			
		}
		
		
		public void update(){
			
			if(dead){
				if(currentAction != DEATH){
					currentAction = DEATH;
					animation.setFrames(sprites.get(DEATH));
					animation.setDelay(100);
					width = 30;
				}
				return;
			}
			
			// update position
			getNextPosition();
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
			
			if(currentAction == SHOOTING) {
				if(animation.hasPlayedOnce()) shooting = false;
			}
			
			
			if(coins > maxCoins) coins = maxCoins;
			if(shooting && currentAction != SHOOTING) {
					coins -= 1;
					Projectile c = new Projectile(tileMap, facingRight,"coin");
					c.setPosition(x, y);
					coinProjectiles.add(c);
				
			}
			
			// update coin Projectiles
			for(int i = 0; i < coinProjectiles.size(); i++) {
				coinProjectiles.get(i).update();
				if(coinProjectiles.get(i).shouldRemove()) {
					coinProjectiles.remove(i);
					i--;
				}
			}
			
			// check done flinching
			if(flinching) {
				long elapsed =
					(System.nanoTime() - flinchTimer) / 1000000;
				if(elapsed > 1000) {
					flinching = false;
				}
			}
			
			
			if(shooting) {
				if(currentAction != SHOOTING) {
					currentAction = SHOOTING;
					animation.setFrames(sprites.get(SHOOTING));
					animation.setDelay(100);
					width = 30;
				}
			} else if(dy > 0) {
				if(currentAction != FALLING) {
					currentAction = FALLING;
					animation.setFrames(sprites.get(FALLING));
					animation.setDelay(100);
					width = 30;
				}
				
			} else if(dy < 0) {
				if(currentAction != JUMPING) {
					currentAction = JUMPING;
					animation.setFrames(sprites.get(JUMPING));
					animation.setDelay(-1);
					width = 30;
				}
			}
			else if(left || right) {
				if(currentAction != WALKING) {
					currentAction = WALKING;
					animation.setFrames(sprites.get(WALKING));
					animation.setDelay(40);
					width = 30;
				}
			}
			else {
				if(currentAction != IDLE) {
					currentAction = IDLE;
					animation.setFrames(sprites.get(IDLE));
					animation.setDelay(400);
					width = 30;
				}
			}
			
			animation.update();
			
			// set direction
			if(currentAction != SHOOTING) {
				if(right) facingRight = true;
				if(left) facingRight = false;
			}
			
		}
		
		
		public void draw(Graphics2D g){
			setMapPosition();
			
			// draw coin Projectiles
			for(int i = 0; i < coinProjectiles.size(); i++) {
				coinProjectiles.get(i).draw(g);
			}
			
			// draw player
			if(flinching) {
				long elapsed =
					(System.nanoTime() - flinchTimer) / 1000000;
				if(elapsed / 100 % 2 == 0) {
					return;
				}
			}
			
			super.draw(g);
		}
		
		
		
		
}
