package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Order;
import com.kea.shipsandsails.Model.Ship;
import com.kea.shipsandsails.Model.Weather;

import java.util.List;

public interface IMove
{
    public void movement(List<Ship> ships, List<Order> orders, Weather weather); //move ships and resolve collisions
}
