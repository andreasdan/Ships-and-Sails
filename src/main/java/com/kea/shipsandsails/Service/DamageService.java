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

    public boolean isHit10(Ship ship, Order order)
    {
        Random r = new Random(10);
        int tal;
        Coordinate target = order.getTarget();

        for(Coordinate pos : order.getCoordinateList()) {

            if (pos.equals(target)) {
                tal = r.nextInt();
                if (tal == 1) {
                    return true;
                }
            }
        }
        return false;

    }
    @Override
    public void resolveAttackOwn(List<Ship> ships, List<Order> orders) {
        //her skal være et forloop i et forloop så alle skibes tjekkes med alle ordre!

        for(int i=0; i<orders.size(); i++)
        {
            Ship ship = ships.get(i);
            Order order = orders.get(i);

            if(isHit40(ship, order))
            {
                int ammuType = order.getAmmunitionType();
                int firePower = order.getFirePower();
                double damage = 0.4 * firePower;

                switch (ammuType)
                {
                    case 0:
                        ship.setHull_health(ship.getHull_health() - damage);
                        break;

                    case 1:
                        ship.setSail_health(ship.getSail_health() - damage);
                        break;

                    case 2:
                        ship.setSailors(ship.getSailors() - damage);
                        break;
                }
                if(isCritical())
                {
                    Random r = new Random(20);
                    int tal = r.nextInt();
                    if(tal == 1)
                    {
                        switch (ammuType)
                        {
                            case 0:
                                shipService.delete();
                                break;

                            case 1:
                                ship.setSail_health(//ship breaks 1/turn??);
                                break;

                            case 2:
                                ship.setSailors(ship.getSailors() / 2);
                                break;
                        }

                    }
                    else if(tal == 2)
                    {
                        switch (ammuType)
                        {
                            case 0:
                                shipService.deleteTurn();
                                break;

                            case 1:
                                ship.setSail_health(ship.getSail_health() / 3);
                                break;

                            case 2:
                                ship.setSailors(ship.getSailors() / 4);
                                break;
                        }

                    }
                }
            }
        }
        //return shipList?? bør metoden ikke sende skibene tilbage?

    }

    @Override
    public void evaluateVictoryConditions(List<Ship> ships, List<Order> orders) {

    }
}
