/**
 * @Author Daniel Blom
 * CommunicationService is a communications wrapper.
 */
package com.kea.shipsandsails.Service.communication;

import com.kea.shipsandsails.communication.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;

@Service
public class CommunicationService implements ISocketListener
{
    private static final int PORT = 1337;
    private ISocketServer socketServer = null;
    private Thread socketServerThread = null;
    private ISocketClient client = null;
    private HashSet<ICommunicationListener> listeners = new HashSet<>();

    public boolean host() {
        boolean success = false;

        // stop already running service
        if(socketServer != null && socketServer.isRunning()) {
            socketServerThread.interrupt();
            socketServer.stopService();
        }

        // start new service in a new thread
        try {
            socketServer = new SocketServer(PORT, this);

            socketServerThread = new Thread((Runnable) socketServer);
            socketServerThread.start();

            // timeout for how long we wait on server startup.
            long timeoutMs = 10000;
            long start = System.currentTimeMillis();
            while (!socketServer.isRunning()){
                Thread.currentThread().wait(200);

                // if timeout
                if(System.currentTimeMillis() > (start + timeoutMs)) {
                    socketServerThread.interrupt();
                    socketServer.stopService();
                    break;
                }
            }

            success = socketServer.isRunning();
        }
        catch (IOException|InterruptedException ex) {
            if(ex instanceof IOException)
                System.out.println("Could not listen on port: "+ PORT + ".");
            System.out.println(ex.getMessage());
        }

        return success;
    }

    public boolean connect(String ip) {
        boolean success = false;

        try {
            Socket socket = new Socket(ip, PORT);
            client = new SocketClient(socket);
            success = true;
        }
        catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

        return success;
    }

    public void addListener(ICommunicationListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ICommunicationListener listener) {
        listeners.remove(listener);
    }

    public boolean isHost() {
        return socketServer != null && socketServer.isRunning();
    }

    public boolean isClient() {
        return client != null && client.isConnected();
    }

    public void sendMessage(String message) {
        if(isClient())
            client.sendMessage(message);
        /*if(isHost())
            socketServer.broadcast(message);*/
    }

    public String sendMessageAndWait(String message) {
        String result = null;

        if(isClient())
            client.sendMessageAndWait(message);

        return result;
    }

    @Override
    public synchronized void onClientConnect(ISocketClient client) {
        this.client = client;
    }

    @Override
    public synchronized void onClientDisconnect(ISocketClient client) {
        this.client = null;
    }

    @Override
    public synchronized void onMessageReceived(String message, ISocketClient client) {
        listeners.forEach(x -> x.onMessageReceived(message, client));
    }
}
