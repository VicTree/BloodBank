package main;

import javax.swing.JFrame;

import main.GamePanel;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Game");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setVisible(true);
		
	}
	
}