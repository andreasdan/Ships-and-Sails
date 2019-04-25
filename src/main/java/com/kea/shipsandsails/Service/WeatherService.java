package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Direction;
import com.kea.shipsandsails.Model.Weather;
import com.kea.shipsandsails.Service.communication.CommunicationService;
import com.kea.shipsandsails.Service.communication.ICommunicationListener;
import com.kea.shipsandsails.communication.ISocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WeatherService implements ICommunicationListener {
    @Autowired
    private CommunicationService communicationService;

    private Weather currentWeather;
    private Random rnd;

    public Weather getCurrentWeather() {
        // if client
        if(communicationService.isClient())
            // return fetched remote weather
            return fetchRemoteWeather();

        // return local weather
        return currentWeather;
    }

    public WeatherService() {
        rnd = new Random();
        generateWeather();
    }


    public void generateWeather() {
        // pick random wind direction
        int windDirectionPick = rnd.nextInt(Direction.values().length);
        Direction windDirection = Direction.values()[ windDirectionPick ];

        // pick random wind speed [0;5]
        int windSpeed = rnd.nextInt(5-0 + 1) + 0;

        // replace current weather
        currentWeather = new Weather(windDirection, windSpeed);
    }

    @Override
    public void onMessageReceived(String message, ISocketClient client) {
        // if weather is requested
        if(message.equals("W:"))
            // send weather to client
            client.sendMessage("W:" + currentWeather.toString());
    }

    private Weather fetchRemoteWeather() {
        Weather result = null;

        String weatherString = communicationService.sendMessageAndWait("W:");
        Direction windDirection = Direction.values()[ 0 ];
        int windSpeed = 3;

        result = new Weather(windDirection, windSpeed);

        return result;
    }
}
