package com.xindong.tank.messages;

import com.xindong.tank.objects.GameClient;
import com.xindong.tank.objects.Tank;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankDeadMessage implements Message {
    int msgType = Message.TANK_DEAD_MSG;
    int id;
    GameClient gc;

    public TankDeadMessage(GameClient gc) {
        this.gc = gc;
    }

    public TankDeadMessage(int id) {
        this.id = id;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
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
            int id = dis.readInt();
            if (gc.myTank.id == id) return;
            for (Tank t : gc.tanks) {
                if (t.id == id) {
                    t.setLive(false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
