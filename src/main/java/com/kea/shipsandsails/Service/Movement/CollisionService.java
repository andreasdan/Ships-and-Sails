/**
 * @Author Andreas Dan Petersen
 * CollisionService tager sig af at tjekke for om der opstår collision når et skib flytter sig, samt udregne skaden taget
 */

package com.kea.shipsandsails.Service.Movement;

import com.kea.shipsandsails.Model.Coordinate;
import com.kea.shipsandsails.Model.Ship;

import java.util.List;

public class CollisionService
{
    //needs to be called whenever the ships move one hex
    public List<Ship> updateCollisions(List<Ship> ships, String ownNationality)
    {
        for (int i = 0; i < ships.size(); i++)
        {
            Ship currentShip = ships.get(i);

            // check if the ship is of own nationality (which is our responsibility)
            if (currentShip.getNationality().equals(ownNationality)) {

                Coordinate position = currentShip.getCoordinate();

                for (int j = 0; j < ships.size(); j++) {
                    Ship currentCheckingShip = ships.get(j);

                    // check if coordinate is that same and shipId's are different (if so, collision happened)
                    if (currentCheckingShip.getCoordinate() == position && currentCheckingShip.getShipId() != currentShip.getShipId()) {
                        //record the hull health before calculation to avoid bias
                        int ownHullHealth = currentShip.getHull_health();
                        int otherHullHealth = currentCheckingShip.getHull_health();

                        //update hull health, parent caller should check for 0 hull health (sunken ship)
                        currentShip.setHull_health(updateRemainingHullHealth(ownHullHealth, otherHullHealth));
                        currentCheckingShip.setHull_health(updateRemainingHullHealth(otherHullHealth, ownHullHealth));

                        //update speed to 0
                        currentShip.setSpeed(0);
                        currentCheckingShip.setSpeed(0);

                        //update the list of ships
                        ships.set(i, currentShip);
                        ships.set(j, currentCheckingShip);
                    }
                }
            }
        }

        return ships;
    }

    public int updateRemainingHullHealth(int currentHealth, int otherShipHullHealth)
    {
        int newHealth = currentHealth - otherShipHullHealth / 3;

        // hull health must not be negative
        if (newHealth < 0)
            newHealth = 0;

        return newHealth;
    }
}
