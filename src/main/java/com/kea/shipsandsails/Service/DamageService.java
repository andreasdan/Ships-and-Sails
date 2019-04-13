/**
 * @Author: Sofie Vonge Jensen
 * DamageService tager sig af at udregne skade på egne skibe og evaluere om spillet er slut
 */

package com.kea.shipsandsails.Service;

import com.kea.shipsandsails.Model.Order;
import com.kea.shipsandsails.Model.Ship;

import java.util.List;

public class DamageService implements IAttack{
    @Override
    public void resolveAttackOwn(List<Ship> ships, List<Order> orders) {

    }

    @Override
    public void evaluateVictoryConditions(List<Ship> ships, List<Order> orders) {

    }
}
