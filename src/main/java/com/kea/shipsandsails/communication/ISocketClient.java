package com.kea.shipsandsails.communication;

public interface ISocketClient {
    String sendMessageAndWait(String message);
    void sendMessage(String message);
    boolean isConnected();
}
