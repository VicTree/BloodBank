package entity;

	import java.awt.*;
import java.awt.image.BufferedImage;

	import javax.imageio.ImageIO;

import tileMap.TileMap;
	public class PickUps extends MapObject{
		private boolean pickedup;
		private boolean remove;
		private BufferedImage[] sprites;
		private BufferedImage[] pickedUpSprites;
		
		private long time;
		
		
		public PickUps(TileMap tm, String filename){
			super(tm);
			
			time = System.nanoTime();
			
			width = 15;
			height = 15; 
			cwidth = 15;  
			cheight = 15; 
			
			fallSpeed = 0.15;
			maxFallSpeed = 4.0;
	
			
			// load sprites
			try {
				
				BufferedImage spritesheet = ImageIO.read( 
					getClass().getResourceAsStream(
						"/pickUps/" +filename + ".png"
					)
				);
				
				sprites = new BufferedImage[5]; //number subject to change
				for(int i = 0; i < sprites.length; i++) {
					sprites[i] = spritesheet.getSubimage(
							i * width,
						0,
						width,
						height
					);
				}
			
				pickedUpSprites = new BufferedImage[6]; //number subject to change
				for(int i = 0; i < pickedUpSprites.length; i++) {
					pickedUpSprites[i] = spritesheet.getSubimage(
						i * width,
						height,
						width,
						height
					);
				}
				
				
				
				animation = new Animation();
				animation.setRepeatLength(0, 4);
				animation.setFrames(sprites);
				animation.setDelay(150);
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
			
		}	
	
		public void setPickedUp() {
			if(pickedup) return;
			pickedup = true;
			animation.setRepeatLength(0, 6);
			animation.setFrames(pickedUpSprites);
			animation.setDelay(100);
		}
		
		public boolean shouldRemove() { return remove; }
		
		public void update() {
			
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
			
//			if(!pickedup) {
//				setPickedUp();
//			}
			
			if(System.nanoTime() - time>1000){
			animation.update();
			
			}
			
			if(pickedup && animation.hasPlayedOnce()) {
				remove = true;
			}
			
			
			if(falling) {	
				dy += fallSpeed;
				if(dy > maxFallSpeed) dy = maxFallSpeed;
				
			}
			
		}
		
		public void draw(Graphics2D g) {
			
			setMapPosition();
			
			super.draw(g);
			
		}
		
}
