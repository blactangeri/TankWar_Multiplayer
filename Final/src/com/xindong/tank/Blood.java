package com.xindong.tank;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.awt.*;
import java.util.Random;



public class Blood {
	int x, y, w, h;
	GameLauncher gl; 
	
	private static Random r = new Random();
	
	private boolean live = true;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image life = tk.getImage(Food.class.getClassLoader().getResource("images/symbol.gif"));
	

	
	
	
	public Blood() {
		int rn1 = r.nextInt(900)+ 50;
		int rn2 = r.nextInt(900)+ 50;
		x = rn1;
		y = rn2;
		w = h = 25;
	}
	public Blood(int x, int y) {
		
		this.x = x;
		this.y = y;
		w = h = 25;
	}
	public void draw(Graphics g) {
		
		if(!live) return;
		
		g.drawImage(life, x, y, w, h, null);
	}

	
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w , h);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
}
