package com.xindong.tank.objects;

import com.xindong.tank.network.NetClient;
import com.xindong.tank.network.TankServer;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameClient extends Frame {

    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 768;

    Random r = new Random();
    boolean appear = false;
    boolean victory = false;

    // start music
    AudioClip startMusic = Applet.newAudioClip(getClass().getResource(
            "/sounds/start.wav"));

    Tank myTank = new Tank(700, 700, 0, Direction.STOP, this);

    List<Wall> walls = new ArrayList<Wall>();
    List<Explode> explodes = new ArrayList<Explode>();
    List<Missile> missiles = new ArrayList<Missile>();
    List<Tank> tanks = new ArrayList<Tank>();

    Image offScreenImage = null;

    public int level;

    Blood b = new Blood();
    Food f = new Food();

    //network
    NetClient nc = new NetClient();

    public void paint(Graphics g) {

        if (level >= 2) {
            f.draw(g);
        }
        if (appear) {
            b.draw(g);
        }

        if (tanks.size() <= 0 && level < 4) {
            level++;
            appear = true;
            for (int i = 0; i < Integer.parseInt(PropertyMgr
                    .getProperty("reProduceTankCount")); i++) {
                tanks.add(new Tank(30 + 220 * i, 50, level, Direction.D, this));
            }
        }

        if (tanks.size() <= 0 && level >= 4) {
            victory = true;
        }


        for (int i = 0; i < 7; i++) {
            walls.add(new Wall(200 + i * 100, 250, 40, 40, this));
            walls.add(new Wall(200 + i * 100, 500, 40, 40, this));

            walls.get(i).draw(g);
        }
        for (int i = 0; i < 14; i++) {
            walls.get(i).draw(g);
        }

        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTanks(tanks);
            m.hitTank(myTank);
            m.hitWalls(walls);
            m.hitMissiles(missiles);
            if (!m.isLive())
                missiles.remove(m);
            else
                m.draw(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            t.collidesWithWalls(walls);
            t.collidesWithTanks(tanks);
            t.draw(g);
        }

        if (myTank.isLive())
            myTank.draw(g);
        myTank.collidesWithWalls(walls);
        myTank.collidesWithTanks(tanks);
        myTank.eat(b);
        myTank.eat(f);

    }

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);

    }

    public void lauchFrame() {
        level = 1;
        victory = false;
        startMusic.play();
        int initTankCount = Integer.parseInt(PropertyMgr
                .getProperty("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            tanks.add(new Tank(30 + 220 * i, 50, 1, Direction.D, this));
        }

        // this.setLocation(400, 300);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TankWar");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.black);

        this.addKeyListener(new KeyMonitor());

        setVisible(true);

        new Thread(new PaintThread()).start();

        nc.connect("127.0.0.1", TankServer.TCP_PORT);
    }

    private class PaintThread implements Runnable {

        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(42);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }
    }


}
