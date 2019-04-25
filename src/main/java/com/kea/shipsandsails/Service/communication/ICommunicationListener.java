package com.kea.shipsandsails.Service.communication;

import com.kea.shipsandsails.communication.ISocketClient;
public interface ICommunicationListener {
    void onMessageReceived(String message, ISocketClient client);
}
