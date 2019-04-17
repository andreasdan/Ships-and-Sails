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
        //burde denne ikke returnere noget? fx en String med "vinderen er: "
        //og hvorfor skal vi have en liste af ordre med? vi evaluerer vel på den status, vi sender hinanden
        //efter ordrene er blevet udført så vi skal kun bruge nr på runden
    evalueteAfterMaxTurn(ships, order);
    evaluateAfterSailors(ships);
    evaluateAfterHull(ships);


    }
    public void evalueteAfterMaxTurn(List<Ship> ships, Order order)
    {
        //hvis vi slutter efter et MAX antal runder.
        if(order.getTurnNumber() >= MAX)
        {
            int sumUs = 0;
            int sumThem = 0;

            for(int i = 0; i < ships.size(); i++)
            {
                Ship ship = ships.get(i);
                if(ship.getNationality().equals("us")) {
                    sumUs += ship.getHull_health() + ship.getSailors() + ship.getSail_health();
                }
                else if(ship.getNationality().equals("them"))
                {
                    sumThem += ship.getHull_health() + ship.getSailors() + ship.getSail_health();
                }
            }
            if(sumUs > sumThem)
            {
                System.out.println("We won!");
            }
            else if (sumThem > sumUs)
            {
                System.out.println("We lost!");
            }
            else
            {
                System.out.println("Tie!");
            }
        }
    }

    public void evaluateAfterSailors(List<Ship> ships)
    {
        int weWon = 0;
        int theyWon = 0;

        for(int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            int sailors = ship.getSailors();
            if(ship.getNationality().equals("us") && sailors >= 3)
            {
                weWon++;
            }
            if(ship.getNationality().equals("them") && sailors >= 3) {
                theyWon++;
            }

        }
        if(weWon == 0 && theyWon > 0)
        {
            System.out.println("We lost!");
        }

        else if(weWon > 0 && theyWon == 0)
        {
            System.out.println("We won!");
        }
        else
        {
            System.out.println("Tie!");
        }

    }

    public void evaluateAfterHull(List<Ship> ships)
    {
        int weWon = 0;
        int theyWon = 0;

        for(int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            int hull = ship.getHull_health();
            if(ship.getNationality().equals("us") && hull > 0)
            {
                weWon++;
            }
            if(ship.getNationality().equals("them") && hull > 0) {
                theyWon++;
            }

        }
        if(weWon == 0 && theyWon > 0)
        {
            System.out.println("We lost!");
        }

        else if(weWon > 0 && theyWon == 0)
        {
            System.out.println("We won!");
        }
        else
        {
            System.out.println("Tie!");
        }
    }
}
