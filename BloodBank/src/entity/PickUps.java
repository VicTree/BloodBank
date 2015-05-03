package entity;

	import java.awt.*;
import java.awt.image.BufferedImage;

	import javax.imageio.ImageIO;

import tileMap.TileMap;
	public class PickUps extends MapObject{
		private boolean pickedup;
		private boolean remove;
		private BufferedImage[] sprites;
		private BufferedImage[] pickedupsprites;
		
		public PickUps(TileMap tm, String filename){
			super(tm);
			
			width = 30;
			height = 30; 
			cwidth = 14;  
			cheight = 14; 
	
			
			// load sprites
			try {
				
				BufferedImage spritesheet = ImageIO.read( 
					getClass().getResourceAsStream(
						filename
					)
				);
			
				sprites = new BufferedImage[4]; //number subject to change
				for(int i = 0; i < sprites.length; i++) {
					sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height
					);
				}
				
				pickedupsprites = new BufferedImage[4]; //number subject to change
				for(int i = 0; i < pickedupsprites.length; i++) {
					pickedupsprites[i] = spritesheet.getSubimage(
						i * width,
						height,
						width,
						height
					);
				}
				
				animation = new Animation();
				animation.setFrames(sprites);
				animation.setDelay(70);
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}	
	
		public void setPickedUp() {
			if(pickedup) return;
			pickedup = true;
			animation.setFrames(pickedupsprites);
			animation.setDelay(70);
		}
		
		public boolean shouldRemove() { return remove; }
		
		public void update() {
			
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
			
			if(!pickedup) {
				setPickedUp();
			}
			
			
			animation.update();
			if(pickedup && animation.hasPlayedOnce()) {
				remove = true;
			}
			
		}
		
		public void draw(Graphics2D g) {
			
			setMapPosition();
			
			super.draw(g);
			
		
}}
