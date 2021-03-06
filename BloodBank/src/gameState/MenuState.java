package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tileMap.Background;

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0;
	BufferedImage title;
	private String[] options;
	private String[] standardOptions = {"Start", "Help", "Quit"};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Color oColor;
	private Color oColor2;
	
	public MenuState(GameStateManager gsm, String background,String title, String[] options,
			Color titleColor, Color oColor, Color oColor2, Font titleFont, Font font) {
		
		this.gsm = gsm;
		this.options = options;
		try {
			this.title = ImageIO.read(
					getClass().getResourceAsStream("/menus/" + title + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.oColor = oColor;
		this.oColor2 = oColor2;
		try {
			
			bg = new Background(background, 1);
			bg.setVector(-0.1, 0);
			
			this.titleColor = titleColor;
			this.titleFont = titleFont; //new Font("Century Gothic",Font.PLAIN,28);
			
			this.font = font; //new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		options = standardOptions;
		oColor = Color.CYAN;
		oColor2 = Color.BLACK;
		bg = null;
		
		titleColor = new Color(120,0,0);
		titleFont = new Font("Century Gothic",Font.PLAIN,28);
		font = new Font("Arial", Font.PLAIN, 12);
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		try{
		bg.draw(g);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawImage(title,0,0, (int)(title.getWidth()*3),(int)(title.getHeight() * 3),null);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(oColor);
			}
			else {
				g.setColor(oColor2);
			}
			g.drawString(options[i],72 +  i * 150, 265);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVELSELECTOR);
		}
		if(currentChoice == 1) {
			// help
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_LEFT) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_RIGHT) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
}










