package com.xindong.tank.objects;

import java.awt.*;

public class Wall {
	int x, y, w, h;
	GameClient gl ;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image iron = tk.getImage(Wall.class.getClassLoader().getResource("images/iron.gif")); 
	
	public Wall(int x, int y, int w, int h, GameClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.gl = tc;
	}
	
	public void draw(Graphics g) {
		g.drawImage(iron, x, y, w, h, null);
	}
	
	public Rectangle getRect() {
		
		return new Rectangle(x-5, y-5, w+1, h+1);
		
	}
}
