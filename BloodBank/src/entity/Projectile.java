package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import tileMap.TileMap;

public class Projectile extends MapObject {
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	public Projectile(TileMap tm, boolean right, String filename) {
		
		super(tm);
		
		facingRight = right;
		
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		fallSpeed = .3; //might
		maxFallSpeed = 4; //need
		dy = fallSpeed;  //fix
		
		width = 24;
		height = 9;
		cwidth = 24;
		cheight = 9;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/projectiles/" + filename + ".png"
				)
			);
			
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
			hitSprites = new BufferedImage[4];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(
					i * width,
					height,
					width,
					height
				);
			}
			
			animation = new Animation();
			animation.setRepeatLength(0,4);
			animation.setFrames(sprites);
			animation.setDelay(70);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setHit() {
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
}


















