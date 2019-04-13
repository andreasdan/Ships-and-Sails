package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Order;
import com.kea.shipsandsails.Model.Ship;

import java.util.List;

public interface IAttack {

        public void resolveAttackOwn(List<Ship> ships, List<Order> orders); //takes ship and their orders and fire - resolve damage
        public void evaluateVictoryConditions(List<Ship> ships, List<Order> orders); //max turns, no ships moving, ships eliminated

}
