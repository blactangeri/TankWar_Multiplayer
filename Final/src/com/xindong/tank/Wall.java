package com.xindong.tank;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import java.awt.*;

public class Wall {
	int x, y, w, h;
	GameLauncher gl ;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image iron = tk.getImage(Wall.class.getClassLoader().getResource("images/iron.gif")); 
	
	public Wall(int x, int y, int w, int h, GameLauncher tc) {
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
