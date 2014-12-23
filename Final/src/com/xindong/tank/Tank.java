package com.xindong.tank;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Tank {
	public int XSPEED;
	public int YSPEED;
	
	private boolean live = true;
	private BloodBar bb = new BloodBar();

	public static final int INITIALHP = 100;
	public static final int INITIALHP2 = 200;
	private int HP;

	GameLauncher gl;

	private int type;
	private boolean good;

	private int x, y;
	private int oldX, oldY;

	private static Random r = new Random();

	private boolean bL = false, bU = false, bR = false, bD = false;

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.U;

	private int step = r.nextInt(12) + 7;

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] myTankImages1 = null;
	private static Image[] myTankImages2 = null;

	private static Image[] enemyTankImages1 = null;
	private static Image[] enemyTankImages2 = null;
	private static Image[] enemyTankImages3 = null;
	private static Image[] enemyTankImages4 = null;

	private static Map<String, Image> myImgs1 = new HashMap<String, Image>();
	private static Map<String, Image> myImgs2 = new HashMap<String, Image>();

	private static Map<String, Image> eImgs1 = new HashMap<String, Image>();
	private static Map<String, Image> eImgs2 = new HashMap<String, Image>();
	private static Map<String, Image> eImgs3 = new HashMap<String, Image>();
	private static Map<String, Image> eImgs4 = new HashMap<String, Image>();

	// add sound
	AudioClip shotSound = Applet.newAudioClip(getClass().getResource(
			"/sounds/shot.wav"));
	AudioClip eatSound = Applet.newAudioClip(getClass().getResource(
			"/sounds/life.wav"));

	static {

		myTankImages1 = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-l.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-lu.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-u.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-ru.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-r.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-rd.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-d.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero1-ld.png")) };
		
	
		myTankImages2 = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-l.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-lu.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-u.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-ru.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-r.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-rd.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-d.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/hero2-ld.png")) };

		enemyTankImages1 = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tankLD.gif")) };

		enemyTankImages2 = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2l.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2lu.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2u.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2ru.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2r.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2rd.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2d.png")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank2ld.png")) };

		enemyTankImages3 = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3l.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3lu.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3u.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3ru.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3r.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3rd.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3d.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank3ld.gif")) };

		enemyTankImages4 = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4l.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4lu.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4u.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4ru.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4r.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4rd.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4d.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource(
						"images/tank4ld.gif")) };

		myImgs1.put("L", myTankImages1[0]);
		myImgs1.put("LU", myTankImages1[1]);
		myImgs1.put("U", myTankImages1[2]);
		myImgs1.put("RU", myTankImages1[3]);
		myImgs1.put("R", myTankImages1[4]);
		myImgs1.put("RD", myTankImages1[5]);
		myImgs1.put("D", myTankImages1[6]);
		myImgs1.put("LD", myTankImages1[7]);
		
		myImgs2.put("L", myTankImages2[0]);
		myImgs2.put("LU", myTankImages2[1]);
		myImgs2.put("U", myTankImages2[2]);
		myImgs2.put("RU", myTankImages2[3]);
		myImgs2.put("R", myTankImages2[4]);
		myImgs2.put("RD", myTankImages2[5]);
		myImgs2.put("D", myTankImages2[6]);
		myImgs2.put("LD", myTankImages2[7]);

		eImgs1.put("L", enemyTankImages1[0]);
		eImgs1.put("LU", enemyTankImages1[1]);
		eImgs1.put("U", enemyTankImages1[2]);
		eImgs1.put("RU", enemyTankImages1[3]);
		eImgs1.put("R", enemyTankImages1[4]);
		eImgs1.put("RD", enemyTankImages1[5]);
		eImgs1.put("D", enemyTankImages1[6]);
		eImgs1.put("LD", enemyTankImages1[7]);

		eImgs2.put("L", enemyTankImages2[0]);
		eImgs2.put("LU", enemyTankImages2[1]);
		eImgs2.put("U", enemyTankImages2[2]);
		eImgs2.put("RU", enemyTankImages2[3]);
		eImgs2.put("R", enemyTankImages2[4]);
		eImgs2.put("RD", enemyTankImages2[5]);
		eImgs2.put("D", enemyTankImages2[6]);
		eImgs2.put("LD", enemyTankImages2[7]);
		
		eImgs3.put("L", enemyTankImages3[0]);
		eImgs3.put("LU", enemyTankImages3[1]);
		eImgs3.put("U", enemyTankImages3[2]);
		eImgs3.put("RU", enemyTankImages3[3]);
		eImgs3.put("R", enemyTankImages3[4]);
		eImgs3.put("RD", enemyTankImages3[5]);
		eImgs3.put("D", enemyTankImages3[6]);
		eImgs3.put("LD", enemyTankImages3[7]);
		
		eImgs4.put("L", enemyTankImages4[0]);
		eImgs4.put("LU", enemyTankImages4[1]);
		eImgs4.put("U", enemyTankImages4[2]);
		eImgs4.put("RU", enemyTankImages4[3]);
		eImgs4.put("R", enemyTankImages4[4]);
		eImgs4.put("RD", enemyTankImages4[5]);
		eImgs4.put("D", enemyTankImages4[6]);
		eImgs4.put("LD", enemyTankImages4[7]);
	}

	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;

	public Tank(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.type = type;
		
	}

	public Tank(int x, int y, int type, Direction dir, GameLauncher gl) {
		this(x, y, type);
		this.dir = dir;
		this.gl = gl;
		if (type == 0 ) {
			XSPEED = 5;
			YSPEED = 5;
			HP = 100;
		}
		else if (type == 1) {
			XSPEED = 3;
			YSPEED = 3;
			HP = 20;
		}
		else if (type == 2) {
			XSPEED = 5;
			YSPEED = 5;
			HP = 60; 
		}
		else if (type == 3) {
			XSPEED = 15;
			YSPEED = 15;
			HP = 20;
		}
		else if (type == 4) {
			XSPEED = 3;
			YSPEED = 3;
			HP = 200;
		}
		else if (type == 5) {
			HP = 200;
			XSPEED = 12;
			YSPEED = 12;
		}
		
	}

	public void draw(Graphics g) {
		
		if (!live) {
				if (!good) {
					gl.tanks.remove(this);
				}
		}

		if (type == 0 || type == 5 || type == 2 || type == 4 )
			bb.draw(g);

		if (type == 0) {
			switch (ptDir) {
			case L:
				g.drawImage(myImgs2.get("L"), x, y, null);
				break;
			case LU:
				g.drawImage(myImgs2.get("LU"), x, y, null);
				break;
			case U:
				g.drawImage(myImgs2.get("U"), x, y, null);
				break;
			case RU:
				g.drawImage(myImgs2.get("RU"), x, y, null);
				break;
			case R:
				g.drawImage(myImgs2.get("R"), x, y, null);
				break;
			case RD:
				g.drawImage(myImgs2.get("RD"), x, y, null);
				break;
			case D:
				g.drawImage(myImgs2.get("D"), x, y, null);
				break;
			case LD:
				g.drawImage(myImgs2.get("LD"), x, y, null);
				break;
			}
		} 
		
		else if (type == 5) {
			switch (ptDir) {
			case L:
				g.drawImage(eImgs2.get("L"), x, y, null);
				break;
			case LU:
				g.drawImage(eImgs2.get("LU"), x, y, null);
				break;
			case U:
				g.drawImage(eImgs2.get("U"), x, y, null);
				break;
			case RU:
				g.drawImage(eImgs2.get("RU"), x, y, null);
				break;
			case R:
				g.drawImage(eImgs2.get("R"), x, y, null);
				break;
			case RD:
				g.drawImage(eImgs2.get("RD"), x, y, null);
				break;
			case D:
				g.drawImage(eImgs2.get("D"), x, y, null);
				break;
			case LD:
				g.drawImage(eImgs2.get("LD"), x, y, null);
				break;
			}
		} 
		
		else if (type == 1) {
			switch (ptDir) {
			case L:
				g.drawImage(eImgs1.get("L"), x, y, null);
				break;
			case LU:
				g.drawImage(eImgs1.get("LU"), x, y, null);
				break;
			case U:
				g.drawImage(eImgs1.get("U"), x, y, null);
				break;
			case RU:
				g.drawImage(eImgs1.get("RU"), x, y, null);
				break;
			case R:
				g.drawImage(eImgs1.get("R"), x, y, null);
				break;
			case RD:
				g.drawImage(eImgs1.get("RD"), x, y, null);
				break;
			case D:
				g.drawImage(eImgs1.get("D"), x, y, null);
				break;
			case LD:
				g.drawImage(eImgs1.get("LD"), x, y, null);
				break;
			}
		}

		else if (type == 2) {
			switch (ptDir) {
			case L:
				g.drawImage(myImgs1.get("L"), x, y, null);
				break;
			case LU:
				g.drawImage(myImgs1.get("LU"), x, y, null);
				break;
			case U:
				g.drawImage(myImgs1.get("U"), x, y, null);
				break;
			case RU:
				g.drawImage(myImgs1.get("RU"), x, y, null);
				break;
			case R:
				g.drawImage(myImgs1.get("R"), x, y, null);
				break;
			case RD:
				g.drawImage(myImgs1.get("RD"), x, y, null);
				break;
			case D:
				g.drawImage(myImgs1.get("D"), x, y, null);
				break;
			case LD:
				g.drawImage(myImgs1.get("LD"), x, y, null);
				break;
			}
		}
		
		else if (type == 3) {
			switch (ptDir) {
			case L:
				g.drawImage(eImgs3.get("L"), x, y, null);
				break;
			case LU:
				g.drawImage(eImgs3.get("LU"), x, y, null);
				break;
			case U:
				g.drawImage(eImgs3.get("U"), x, y, null);
				break;
			case RU:
				g.drawImage(eImgs3.get("RU"), x, y, null);
				break;
			case R:
				g.drawImage(eImgs3.get("R"), x, y, null);
				break;
			case RD:
				g.drawImage(eImgs3.get("RD"), x, y, null);
				break;
			case D:
				g.drawImage(eImgs3.get("D"), x, y, null);
				break;
			case LD:
				g.drawImage(eImgs3.get("LD"), x, y, null);
				break;
			}
		}
		
		else if (type == 4) {
			switch (ptDir) {
			case L:
				g.drawImage(eImgs4.get("L"), x, y, null);
				break;
			case LU:
				g.drawImage(eImgs4.get("LU"), x, y, null);
				break;
			case U:
				g.drawImage(eImgs4.get("U"), x, y, null);
				break;
			case RU:
				g.drawImage(eImgs4.get("RU"), x, y, null);
				break;
			case R:
				g.drawImage(eImgs4.get("R"), x, y, null);
				break;
			case RD:
				g.drawImage(eImgs4.get("RD"), x, y, null);
				break;
			case D:
				g.drawImage(eImgs4.get("D"), x, y, null);
				break;
			case LD:
				g.drawImage(eImgs4.get("LD"), x, y, null);
				break;
			}
		}
		
		move();
	}

	void move() {

		this.oldX = x;
		this.oldY = y;

		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}

		if (this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}

		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > GameLauncher.GAME_WIDTH)
			x = GameLauncher.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > GameLauncher.GAME_HEIGHT)
			y = GameLauncher.GAME_HEIGHT - Tank.HEIGHT;
		
		if (type != 0 && type != 5) {
			Direction[] dirs = Direction.values();
			
			if (type == 1) {
				if (step == 0) {
					// step = r.nextInt(12) + 3;
					step = r.nextInt(12) + 25;
					int rn = r.nextInt(dirs.length);
					dir = dirs[rn];
				}
				step--;

				if (r.nextInt(30) > 23)
					this.fire();
			}
			
			if (type == 2) {
				if (step == 0) {

					// step = r.nextInt(12) + 3;
					step = r.nextInt(12) + 15;
					int rn = r.nextInt(dirs.length);
					dir = dirs[rn];
				}
				step--;

				if (r.nextInt(30) > 20)
					this.fire();
			}
			if (type == 3) {
				if (step == 0) {

					// step = r.nextInt(12) + 3;
					step = r.nextInt(12) + 10;
					int rn = r.nextInt(dirs.length);
					dir = dirs[rn];
				}
				step--;

				if (r.nextInt(30) > 23)
					this.fire();
			}
			if (type == 4) {
				if (step == 0) {

					// step = r.nextInt(12) + 3;
					step = r.nextInt(12) + 25;
					int rn = r.nextInt(dirs.length);
					dir = dirs[rn];
				}
				step--;

				if (r.nextInt(30) > 10)
					this.fire();
			}
			
		}
	}

	private void stay() {
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:
			this.live = true;
			this.HP = INITIALHP;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_Q:
			gl.tanks.clear();
		case KeyEvent.VK_W:
			for (Tank t:gl.tanks) t.live=false;
		
		}
		locateDirection();
	}

	void locateDirection() {
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();
	}

	public Missile fire() {
		if (!live)	return null;

		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		
		if (type == 0) {
			Missile m = new Missile(x, y, true, ptDir, this.gl, 1);
			gl.missiles.add(m);
			
				shotSound.play();
			
			return m;
		}
		
		if (type == 3 || type == 1 || type == 2 ) {
			Missile m = new Missile(x, y, false, ptDir, this.gl, 1);
			gl.missiles.add(m);
			if (type == 0 || type == 5) {
				shotSound.play();
			}
			return m;
		}
		if (type == 4) {
			Missile m = new Missile(x, y, false, ptDir, this.gl, 3);
			gl.missiles.add(m);
			
			return m;
		}
		else {
			Missile m = new Missile(x, y, true, ptDir, this.gl, 2);
			gl.missiles.add(m);
			shotSound.play();
			return m;

		}
		
	}

	public Missile fire(Direction dir) {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		if (type == 0) {
			Missile m = new Missile(x, y, true, dir, this.gl, 1);
			gl.missiles.add(m);
			
				shotSound.play();
			
			return m;
		}
		
		if (type == 3 || type == 1 || type == 2 ) {
			Missile m = new Missile(x, y, false, dir, this.gl, 1);
			gl.missiles.add(m);
			if (type == 0 || type == 5) {
				shotSound.play();
			}
			return m;
		}
		if (type == 4) {
			Missile m = new Missile(x, y, false, dir, this.gl, 2);
			gl.missiles.add(m);
			
			return m;
		}
		else {
			Missile m = new Missile(x, y, true, dir, this.gl, 2);
			gl.missiles.add(m);
			shotSound.play();
			return m;

		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH + 3, HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public int getType() {
		return this.type;
	}
	
	public boolean isGood() {
		if (type == 0 || type == 5) {
			return true;
		}
		else return false;
	}

	
	public boolean collidesWithWall(Wall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public void collidesWithWalls(List<Wall> walls) {
		for (int i = 0; i < walls.size(); i++) {
			collidesWithWall(walls.get(i));
		}
	}

	public boolean collidesWithTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}

	private void superFire() {
		Direction[] dirs = Direction.values();
		for (int i = 0; i < 8; i++) {
			fire(dirs[i]);
		}
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hp) {
		this.HP = hp;
	}

	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			
			g.drawRect(x + 9, y - 2, WIDTH, 3);
			if (type == 0) {
				int w = WIDTH * HP / 100;
				g.setColor(Color.RED);
				g.drawRect(x + 9, y - 2, WIDTH, 3);
				g.fillRect(x + 9, y - 2, w, 3);
				g.setColor(c);
			}
			else if (type == 2) {
				int w = WIDTH * HP / 60;
				g.setColor(Color.PINK);
				g.drawRect(x + 9, y - 2, WIDTH, 3);
				g.fillRect(x + 9, y - 2, w, 3);
				g.setColor(c);
			}
			else if (type == 4) {
				int w = WIDTH * HP / 200;
				g.setColor(Color.PINK);
				g.drawRect(x + 9, y - 2, WIDTH, 3);
				g.fillRect(x + 9, y - 2, w, 3);
				g.setColor(c);
			}
			else if (type ==5) {
				int w = WIDTH * HP / 200;
				g.setColor(Color.RED);
				g.drawRect(x + 9, y - 2, WIDTH, 3);
				g.fillRect(x + 9, y - 2, w, 3);
				g.setColor(c);
			}
		}
	}

	public boolean eat(Blood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if (type == 0) {
				this.HP = INITIALHP;
			}
			if (type == 5) {
				this.HP = INITIALHP2;
			}
			eatSound.play();
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	public void eat(Food f) {
		if (this.live && f.isLive() && this.getRect().intersects(f.getRect())) {
			if (type == 0) {
				this.type = 5;
				this.HP = INITIALHP2;
				this.XSPEED = 12;
				this.YSPEED = 12;
			}
			eatSound.play();
			f.setLive(false);
		}
	}
}