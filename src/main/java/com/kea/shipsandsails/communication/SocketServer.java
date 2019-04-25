/**
 * @Author Daniel Blom
 * SocketServer is listening on a port and will
 * notify a listener on changes and when messages are received.
 */

package com.kea.shipsandsails.communication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements ISocketServer, ISocketListener, Runnable
{
    private int port;
    private ServerSocket serverSocket;
    private ISocketListener listener;
    private volatile boolean running = false;

    public boolean isRunning() { return running; }

    public SocketServer(int port, ISocketListener listener) throws IOException {
        this.port = port;
        this.listener = listener;

        serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
    }

    public synchronized void stopService() {
        try {
            serverSocket.close();
        }
        catch (IOException ioEx) {
            System.out.println(ioEx);
        }
    }

    @Override
    public synchronized void onClientConnect(ISocketClient client) {
        // notify listener that a client has connected
        listener.onClientConnect(client);
    }

    @Override
    public synchronized void onClientDisconnect(ISocketClient client) {
        // notify listener that a client has disconnected
        listener.onClientDisconnect(client);
    }

    @Override
    public synchronized void onMessageReceived(String message, ISocketClient client) {
        // notify listener that a message was received
        listener.onMessageReceived(message, client);
    }

    @Override
    public void run() {
        running = true;
        try {
            while (running)
            {
                Socket clientSocket = serverSocket.accept();

                // create necessary object for handling requests
                SocketClient client = new SocketClient(clientSocket);
                SocketRequestHandler requestHandler = new SocketRequestHandler(client);

                // start request handler in a separate thread
                // pooling should be applied instead
                new Thread(requestHandler).start();
            }
        }
        catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }
    }

    private class SocketRequestHandler implements Runnable
    {
        private SocketClient client;
        private DataInputStream in;

        protected SocketRequestHandler(SocketClient client) {
            this.client = client;
            try {
                in = new DataInputStream(client.getSocket().getInputStream());

                // on client connect
                onClientConnect(client);
            }
            catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }
        }

        @Override
        public void run() {
            String input = null;

            try {
                while (client.isConnected()) {
                    input = in.readUTF();

                    // on message received
                    onMessageReceived(input, client);
                }
            }
            catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }

            // on client disconnect
            onClientDisconnect(client);
        }
    }
}
