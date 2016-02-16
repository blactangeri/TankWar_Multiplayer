package com.xindong.tank.network;

import java.io.IOException;
import java.net.Socket;

public class NetClient {
    private static int UDP_PORT_START = 2223;
    private int udpPort;

    public NetClient() {
        udpPort = UDP_PORT_START++;
    }

    public void connect(String IP, int port) {
        try {
            Socket s = new Socket(IP, port);

            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
