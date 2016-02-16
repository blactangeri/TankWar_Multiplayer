package com.xindong.tank.messages;

import com.xindong.tank.objects.Direction;
import com.xindong.tank.objects.GameClient;
import com.xindong.tank.objects.Tank;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMessage implements Message {
    int msgType = Message.TANK_NEW_MSG;

    Tank tank;

    GameClient gc;

    public TankNewMessage(Tank tank) {
        this.tank = tank;
    }

    public TankNewMessage(GameClient gc) {
        this.gc = gc;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(msgType);
            dos.writeInt(tank.id);
            dos.writeInt(tank.x);
            dos.writeInt(tank.y);
            dos.writeInt(tank.dir.ordinal());
            dos.writeInt(tank.type);
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
            if (id == gc.myTank.id) return;

            int x = dis.readInt();
            int y = dis.readInt();
            Direction dir = Direction.values()[dis.readInt()];
            int tankType = dis.readInt();
            boolean exist = false;

            for (int i = 0; i < gc.tanks.size(); ++i) {
                Tank t = gc.tanks.get(i);
                if (t.id == id) {
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                TankNewMessage tnMsg = new TankNewMessage(gc.myTank);
                gc.nc.send(tnMsg);
                Tank t = new Tank(x, y, tankType, dir, gc);
                t.id = id;
                gc.tanks.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
