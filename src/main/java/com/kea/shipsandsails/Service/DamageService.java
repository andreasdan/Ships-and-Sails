/**
 * @Author: Sofie Vonge Jensen
 * DamageService tager sig af at udregne skade på egne skibe og evaluere om spillet er slut
 */

package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Coordinate;
import com.kea.shipsandsails.Model.Order;
import com.kea.shipsandsails.Model.Ship;

import java.util.List;
import java.util.Random;

public class DamageService implements IAttack{

    public boolean isHit40(Ship ship, Order order)
    {
        Random r = new Random(10);
        int tal;
        Coordinate target = order.getTarget();

        for(Coordinate pos : order.getCoordinateList()) {

            if (pos.equals(target)) {
                tal = r.nextInt();
                if (tal < 4) {
                    return true;
                }
            }
        }
           return false;

    }

    //kommer først i spil hvis isHit40 er true
    public boolean isCritical()
    {
        Random r = new Random(4);
        int tal = r.nextInt();

        if(tal == 1)
        {
            return true;
        }
        return false;
    }

    public boolean isHit10(List<Ship> ship, List<Order> order)
    {


        return false;

    }
    @Override
    public void resolveAttackOwn(List<Ship> ships, List<Order> orders) {

        for(int i=0; i<orders.size(); i++)
        {
            Ship ship = ships.get(i);
            Order order = orders.get(i);

            if(isHit40(ship, order))
            {
                int ammuType = order.getAmmunitionType();
            }
        }

    }

    @Override
    public void evaluateVictoryConditions(List<Ship> ships, List<Order> orders) {

    }
}
