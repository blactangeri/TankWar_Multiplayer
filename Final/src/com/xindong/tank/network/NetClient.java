package com.xindong.tank.network;

import com.xindong.tank.messages.Message;
import com.xindong.tank.objects.GameClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

public class NetClient {
    GameClient gc;

    String IP; //server IP
    private int udpPort;
    DatagramSocket ds = null;


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
    }

    public void send(Message msg) {
        msg.send(ds, IP, TankServer.UDP_PORT);
    }


}
