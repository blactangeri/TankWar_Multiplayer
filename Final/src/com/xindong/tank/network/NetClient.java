package com.xindong.tank.network;

import com.xindong.tank.messages.*;
import com.xindong.tank.objects.GameClient;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class NetClient {
    GameClient gc;

    String IP; //server IP
    private int udpPort;
    DatagramSocket ds = null;

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public NetClient(GameClient gc) {
        this.gc = gc;
    }

    public void connect(String IP, int port) {
        this.IP = IP;
        try {
            ds = new DatagramSocket(udpPort);
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket s = null;
        try {
            s = new Socket(IP, port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int id = dis.readInt();
            gc.myTank.id = id;
            gc.myTank.good = id % 2 == 0;
            System.out.println("Client " + id + " connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) try {
                s.close();
                s = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TankNewMessage msg = new TankNewMessage(gc.myTank);
        send(msg);

        new Thread(new UDRReceiverThread()).start();
    }

    public void send(Message msg) {
        msg.send(ds, IP, TankServer.UDP_PORT);
    }

    public class UDRReceiverThread implements Runnable {
        byte[] buf = new byte[1024];

        public void run() {
            while (ds != null) {
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);
                    parse(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void parse(DatagramPacket dp) {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
            DataInputStream dis = new DataInputStream(bais);
            int msgType = 0;
            try {
                msgType = dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message msg = null;
            switch (msgType) {
                case Message.TANK_NEW_MSG:
                    msg = new TankNewMessage(NetClient.this.gc);
                    msg.parse(dis);
                    break;
                case Message.TANK_MOVE_MSG:
                    msg = new TankMoveMessage(NetClient.this.gc);
                    msg.parse(dis);
                    break;
                case Message.MISSILE_NEW_MSG:
                    msg = new MissileNewMessage(NetClient.this.gc);
                    msg.parse(dis);
                    break;
                case Message.MISSILE_DEAD_MSG:
                    msg = new MissileDeadMessage(NetClient.this.gc);
                    msg.parse(dis);
                    break;
                case Message.TANK_DEAD_MSG:
                    msg = new TankDeadMessage(NetClient.this.gc);
                    msg.parse(dis);
                    break;
            }
        }
    }
}
