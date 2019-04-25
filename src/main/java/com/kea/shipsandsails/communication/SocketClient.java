/**
 * @Author Daniel Blom
 * A Socket client for sending and requesting data
 */

package com.kea.shipsandsails.communication;

import java.io.*;
import java.net.Socket;

public class SocketClient implements ISocketClient {
    private Socket socket;
    private PrintWriter out;
    private DataInputStream in;


    protected Socket getSocket() { return this.socket; }

    public SocketClient(Socket socket) {
        this.socket = socket;

        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }
        catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }
    }

    @Override
    public synchronized String sendMessageAndWait(String message) {
        String result = null;

        out.println(message);
        try {
            result = in.readUTF();
        }
        catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

        return result;
    }

    @Override
    public synchronized void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }

}
