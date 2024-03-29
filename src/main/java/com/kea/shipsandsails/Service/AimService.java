/**
 * @Author: Sofie Vonge Jensen
 * AimService tager sig af at sigte på et koordinat
 */

package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Coordinate;
import com.kea.shipsandsails.Model.Direction;
import com.kea.shipsandsails.Model.Order;
import com.kea.shipsandsails.Model.Ship;

public class AimService {

    public boolean legalAim(Ship ship, Order order)
    {
        Coordinate target = order.getTarget();
        Coordinate pos = ship.getCoordinate(); //eller skal dette være sidste plads i ordrelisten af koordinater?
        Direction direction = ship.getDirection();
        int ammu = order.getAmmunitionType();

            if (ship.getShipType() == 1) {
                switch (direction) {
                    case N:
                        return (target.getY() == pos.getY() - 1) && (target.getX() == pos.getX() || target.getX() == pos.getX() - 1);

                    case NE:
                        return (target.getX() == pos.getX() - 1) && (target.getY() == pos.getY() - 1 || target.getY() == pos.getY());

                    //flere cases
                }
            } else if (ship.getShipType() == 2) {
                if(ammu == 1 || ammu == 2)
                {
                    switch (direction)
                    {
                        case NE:
                    }
                }

            }

        return false;

    }

    //skal kun i spil hvis legalAim er true!
    public void aim(Order order, Coordinate pos)
    {
        order.setTarget(pos);
    }
}
