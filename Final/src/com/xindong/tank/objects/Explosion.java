package com.xindong.tank.objects;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explosion {
	int x, y;

	int type;

	private boolean live = true;

	private GameClient gl;

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs1 = {

			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/b1.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/b2.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/b3.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/b4.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/b5.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/b6.png")),

	/*
	 * tk.getImage(Explosion.class.getClassLoader().getResource( "images/6.png")),
	 * tk.getImage(Explosion.class.getClassLoader().getResource( "images/7.png")),
	 * tk.getImage(Explosion.class.getClassLoader().getResource( "images/8.png")),
	 * tk.getImage(Explosion.class.getClassLoader().getResource( "images/9.png")),
	 * tk.getImage(Explosion.class.getClassLoader().getResource( "images/10.png"))
	 */};

	private static Image[] imgs2 = {

			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/1.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/2.png")),
	// tk.getImage(Explosion.class.getClassLoader().getResource(
	// "images/3.png")),
	};

	private static Image[] imgs3 = {

			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/21.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/22.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/23.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/24.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/25.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/26.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/27.png")),
			tk.getImage(Explosion.class.getClassLoader().getResource(
					"images/28.png")), };

	int step = 0;

	private static boolean init = false;

	public Explosion(int x, int y, GameClient tc, int type) {
		this.x = x;
		this.y = y;
		this.gl = tc;
		this.type = type;
	}

	public void draw(Graphics g) {

		if (type == 1) {
			if (!init) {
				for (int i = 0; i < imgs1.length; i++) {
					g.drawImage(imgs1[i], x - 10, y - 10, null);
				}
				init = true;
			}

			if (!live) {
				gl.explosions.remove(this);
				return;
			}

			if (step == imgs1.length) {
				live = false;
				step = 0;
				return;
			}

			g.drawImage(imgs1[step], x - 10, y - 10, null);

			step++;
		} 
		
		if (type == 2) {
			if (!init) {
				for (int i = 0; i < imgs2.length; i++) {
					g.drawImage(imgs2[i], x - 50, y - 50, null);
				}
				init = true;
			}

			if (!live) {
				gl.explosions.remove(this);
				return;
			}

			if (step == imgs2.length) {
				live = false;
				step = 0;
				return;
			}

			g.drawImage(imgs2[step], x - 50, y - 50, null);

			step++;
		}
		
		if (type == 3){
			if (!init) {
				for (int i = 0; i < imgs3.length; i++) {
					g.drawImage(imgs3[i], x - 50, y - 50, null);
				}
				init = true;
			}

			if (!live) {
				gl.explosions.remove(this);
				return;
			}

			if (step == imgs3.length) {
				live = false;
				step = 0;
				return;
			}

			g.drawImage(imgs3[step], x - 50, y - 50, null);

			step++;
		}
		if (type == 4) {
			if (!init) {
				
					g.drawImage(imgs2[0], x - 50, y - 50, null);
				
				init = true;
			}

			if (!live) {
				gl.explosions.remove(this);
				return;
			}

			if (step == imgs2.length) {
				live = false;
				step = 0;
				return;
			}

			g.drawImage(imgs2[0], x - 50, y - 50, null);

			step++;
		}

	}
}
