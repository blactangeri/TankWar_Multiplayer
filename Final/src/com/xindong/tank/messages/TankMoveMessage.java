package com.xindong.tank.messages;

import com.xindong.tank.objects.Direction;
import com.xindong.tank.objects.GameClient;
import com.xindong.tank.objects.Tank;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMessage implements Message{
    int msgType = Message.TANK_MOVE_MSG;
    int x, y, id;
    Direction dir, ptDir;
    GameClient gc;

    public TankMoveMessage(int x, int y, int id, Direction dir, Direction ptDir) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.dir = dir;
        this.ptDir = ptDir;
    }

    public TankMoveMessage(GameClient gc) {
        this.gc = gc;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(ptDir.ordinal());
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
            Direction ptDir = Direction.values()[dis.readInt()];

            for (Tank t : gc.tanks) {
                if (t.id == id) {
                    t.x = x;
                    t.y = y;
                    t.dir = dir;
                    t.ptDir = ptDir;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
