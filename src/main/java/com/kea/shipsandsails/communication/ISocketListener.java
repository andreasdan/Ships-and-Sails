package com.kea.shipsandsails.communication;

public interface ISocketListener {
    void onClientConnect(ISocketClient client);
    void onClientDisconnect(ISocketClient client);
    void onMessageReceived(String message, ISocketClient client);
}
