package com.xindong.tank;


import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Food {

	int x, y, w, h;
	GameLauncher gl;
	private boolean live = true;
	private static Random r = new Random();

	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image star = tk.getImage(Food.class.getClassLoader().getResource("images/star.gif"));
	
	
	
	public Food() {
		int rn1 = r.nextInt(900)+ 50;
		int rn2 = r.nextInt(900)+ 50;
		x = rn1;
		y = rn2;
		w = h = 25;
	}
	public Food(int x, int y) {
		
		this.x = x;
		this.y = y;
		w = h = 25;
	}
	
	
	public void draw(Graphics g) {
		if(!live) return;
		
		g.drawImage(star, x, y, w, h, null);
	}
	
	public boolean isLive() {
		return live;
	}
	public void setLive(Boolean live) {
		this.live = live;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, 30, 30);
	}
}
	
	
