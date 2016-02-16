package com.xindong.tank.messages;

import com.xindong.tank.objects.Direction;
import com.xindong.tank.objects.GameClient;
import com.xindong.tank.objects.Missile;
import com.xindong.tank.objects.Tank;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by James on 2/16/16.
 */
public class MissileNewMessage implements Message {
    int msgType = Message.MISSILE_NEW_MSG;
    GameClient gc;
    Missile m;

    public MissileNewMessage(Missile m) {
        this.m = m;
    }

    public MissileNewMessage(GameClient gc) {
        this.gc = gc;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(m.tankId);
            dos.writeInt(m.id);
            dos.writeInt(m.x);
            dos.writeInt(m.y);
            dos.writeInt(m.type);
            dos.writeInt(m.dir.ordinal());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = baos.toByteArray();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
        try {
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(DataInputStream dis) {
        try {
            int tankId = dis.readInt();
            if (tankId == gc.myTank.id) return;

            int id = dis.readInt();
            int x = dis.readInt();
            int y = dis.readInt();
            int type = dis.readInt();
            Direction dir = Direction.values()[dis.readInt()];
            Missile m = new Missile(x, y, true, dir, gc, type);
            m.id = id;
            gc.missiles.add(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
