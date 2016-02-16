package com.xindong.tank.messages;

import com.xindong.tank.objects.Explosion;
import com.xindong.tank.objects.GameClient;
import com.xindong.tank.objects.Missile;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileDeadMessage implements Message {
    int msgType = Message.MISSILE_DEAD_MSG;
    int tankId;
    int id;
    GameClient gc;

    public MissileDeadMessage(int tankId, int id) {
        this.tankId = tankId;
        this.id = id;
    }

    public MissileDeadMessage(GameClient gc) {
        this.gc = gc;
    }



    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(tankId);
            dos.writeInt(id);
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
            for (Missile m : gc.missiles) {
                if (id == m.id && tankId == m.tankId) {
                    m.live = false;
                    gc.explosions.add(new Explosion(m.x, m.y, gc, 3));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
