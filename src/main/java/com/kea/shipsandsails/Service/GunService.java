/**
 * @Author Sofie Vonge Jensen
 * GunService tager sig af at loade og skyde
 */

package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Order;
import com.kea.shipsandsails.Model.Ship;
import org.springframework.stereotype.Service;

@Service
public class GunService {
//spørgsmål: skal setLoad/getLoad i spil her eller i controlleren? Altså skal den selv tælle ned og op efter hver tur?


    public boolean canFire(Ship ship)
    {
        return ship.getLoad() == 0 && ship.getSailors() >= 3 && ship.hasMoved(); //vi mangler et eller andet til at fortælle om det lykkedes at flytte
    }
    //controlleren skal først tjekke om canFire er true før fire() kan bruges! Før fire bruges skal aiming i spil
    public void fire(Ship ship)
    {
        ship.setLoad(1);
    }

    public boolean canReload(Ship ship, Order order)
    {
        return ship.getCurrentAmmunitionType() == order.getAmmunitionType() && ship.getSailors() >= 3;
    }

    //controlleren skal først tjekke om canReload er true før reload() kan bruges!
    public void reload(Ship ship, Order order)
    {
        ship.setCurrentAmmunitionType(order.getAmmunitionType());
        ship.setLoad(0);
    }

    public void deload(Ship ship, Order order)
    {
        //deloads ammutype bør være -1!
        ship.setCurrentAmmunitionType(order.getAmmunitionType());
        ship.setLoad(2);
    }

    //man kan først loade nyt ammu hvis der først er blevet deloaded i sidste tur
    public boolean canLoadNewAmmu(Ship ship)
    {
        return ship.getCurrentAmmunitionType() == -1 && ship.getSailors() >= 3;
    }

    //controlleren skal først tjekke om canLoadNewAmmu er true før loadNewAmmu() kan bruges!
    public void loadNewAmmu(Ship ship, Order order)
    {
        ship.setCurrentAmmunitionType(order.getAmmunitionType());
        ship.setLoad(0);
    }
}
