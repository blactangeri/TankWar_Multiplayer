package com.xindong.tank.objects;

 
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.util.List;

public class Missile {
	public int XSPEED;
	public int YSPEED;
	public int XSPEED2;
	public int YSPEED2;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;

	int x, y;
	Direction dir;

	private boolean good;
	private int type;
	private boolean live = true;

	private GameClient gl;
	
	//sound
	AudioClip bigSound = Applet.newAudioClip(getClass().getResource("/sounds/b-explosion.wav"));
	AudioClip smallSound = Applet.newAudioClip(getClass().getResource("/sounds/s-explosion.wav"));
	AudioClip wallSound = Applet.newAudioClip(getClass().getResource("/sounds/hit.wav"));

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	//private static Image[] missileImages = null;
	
	private static Image missileImage1 = null;
	private static Image missileImage2 = null;
	private static Image missileImage3 = null;
	private static Image missileImage4 = null;

	
	//private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		
		missileImage1 = tk.getImage(Missile.class.getClassLoader().getResource("images/normal.png"));
		missileImage2 = tk.getImage(Missile.class.getClassLoader().getResource("images/missile.png"));
		missileImage3 = tk.getImage(Missile.class.getClassLoader().getResource("images/bul2.png"));
		//missileImage4 = tk.getImage(Missile.class.getClassLoader().getResource("images/NOR2.png"));

	}

	public Missile(int x, int y, Direction dir, int type) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.type = type;
		
		if (type == 1) {
			XSPEED = 10;
			YSPEED = 10;
		}
		if (type == 2) {
			XSPEED = 15;
			YSPEED = 15;
		}
	}

	public Missile(int x, int y, boolean good, Direction dir, GameClient gl, int type) {
		this(x, y, dir, type);
		this.good = good;
		this.gl = gl;
		
		if (type == 1) {
			XSPEED = 10;
			YSPEED = 10;
		}
		if (type == 2 || type == 3) {
			XSPEED = 18;
			YSPEED = 18;
		}
		
	}

	public void draw(Graphics g) {
		if (!live) {
			gl.missiles.remove(this);
			return;
		}

		if (type == 1) {
			g.drawImage(missileImage1, x+10, y+10, null);
		}
		if (type == 2) {
			g.drawImage(missileImage2, x+10, y+10, null);
		}
		if (type == 3) {
			g.drawImage(missileImage3, x+10, y+10, null);
		}
		/*
		switch (dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		*/

		move();
	}

	private void move() {

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

		if (x < 0 || y < 0 || x > GameClient.GAME_WIDTH
				|| y > GameClient.GAME_HEIGHT) {
			live = false;
		}
	}

	public boolean isLive() {
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	

	public boolean hitTank(Tank t) {
		
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {
			
			if (type == 1){
				t.setHP(t.getHP() - 20);
				if (t.getHP() <= 0){
					bigSound.play();
					t.setLive(false);
					if (t.getType() == 0 || t.getType() == 4 || t.getType() == 5) {
						Explode e = new Explode(x, y, gl, 3);
						gl.explodes.add(e);
					}else {
						Explode e = new Explode(x, y, gl, 1);
						gl.explodes.add(e);
					}
				}else {
					smallSound.play();
					Explode e = new Explode(x, y, gl, 2);
					gl.explodes.add(e);
				}
			}
			
			if(type == 2 || type == 3) {
				t.setHP(t.getHP() - 50);
				if (t.getHP() <= 0){
					bigSound.play();
					t.setLive(false);
					if (t.getType() == 0 || t.getType() == 4 || t.getType() == 5) {
						Explode e = new Explode(x, y, gl, 3);
						gl.explodes.add(e);
					}else {
						Explode e = new Explode(x, y, gl, 1);
						gl.explodes.add(e);
					}
				}else {
					smallSound.play();
					Explode e = new Explode(x, y, gl, 2);
					gl.explodes.add(e);
				}
			}
			this.live = false;
			return true;
		}
		return false;
	}


	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}

	public void hitMissile(Missile m) {
		if (this.live && this.getRect().intersects(m.getRect()) && m.isLive() && this.good!=m.good) {
			Explode e =  new Explode(x, y, gl, 2);
			gl.explodes.add(e);
			//add sound
			smallSound.play();
			m.live = false;
		}		
	}
	
	public void hitMissiles(List<Missile> missles) {
		for (int i = 0; i < missles.size(); i++) {
			hitMissile(missles.get(i));
		}
	}
	
	public boolean hitWall(Wall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			Explode e = new Explode(x, y, gl, 4);
			gl.explodes.add(e);
			if(good) wallSound.play();
			return true;
		}
		return false;
	}
	
	public void hitWalls(List<Wall> walls) {
		for (int i = 0; i < walls.size(); i++) {
			hitWall(walls.get(i));
		}
	}

}
