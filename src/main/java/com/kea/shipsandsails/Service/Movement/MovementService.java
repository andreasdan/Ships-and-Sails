/**
 * @Author Andreas Dan Petersen
 * MovementService tager sig af at skibe skifter retning og flytter
 */

package com.kea.shipsandsails.Service.Movement;

import com.kea.shipsandsails.Model.*;

import java.util.List;

public class MovementService implements IMove
{
    private CollisionService collisionService = new CollisionService();

    @Override
    public void movement(List<Ship> ships, List<Order> orders, Weather weather)
    {
        for (Ship ship : ships)
        {
            ship.setSpeed(calculateShipSpeed(ship));
            if (ship.getSpeed() >= 1) {
                for (Order order : orders)
                {
                    if (order.getShipId() == ship.getShipId()) {
                        // something needs to determine what order to execute when.. does the ship move forward THEN turn or...?
                    }
                }
            }
        }
    }

    public int calculateShipSpeed(Ship ship)
    {
        int sailHealth = ship.getSail_health();
        // ships have 0 speed at 0 health or 1 speed at <= 10% health
        if (sailHealth <= 0) {
            return 0;
        } else if (sailHealth <= 10) {
            return 1;
        }
        else {
            int type = ship.getShipType(); //0 = brig, 1 = ship of the line, 2 = man at war
            int speed = 0;
            switch (type) {
                case 1:
                    if (sailHealth > 50) {
                        speed = 2; //max speed = 2 for brig }
                    } else {
                        speed = 1; //rest is 1 for brig
                    }
                case 2:
                    if (sailHealth > 50) {
                        speed = 5; //max speed = 2 for ship of the line }
                    } else if (sailHealth <= 25) {
                        speed = 2; //ship of the line <= 25
                    } else {
                        speed = 3; //ship of the line <= 50
                    }
                case 3:
                    if (sailHealth > 50) {
                        speed = 4; //max speed = 2 for man of war }
                    } else if (sailHealth <= 25) {
                        speed = 2; //man of war <= 25
                    } else {
                        speed = 3; //man of war <= 50
                    }
            }

            return speed;
        }
    }

    /*public int calculateActualSpeed(int shipSpeed, Weather weather)
      {
        //TODO: take weather into consideration
      }*/

    public Direction turnLeft(Direction currentDirection, boolean sharpTurn)
    {
        Direction newDirection = currentDirection;

        if (currentDirection == Direction.N) {
            newDirection = Direction.NW;
        } else {
            newDirection = Direction.values()[currentDirection.getId() - 1];
        }

        //recursive, will turn once more to make a sharp turn
        if (sharpTurn) {
            newDirection = turnLeft(newDirection, false);
        }

        return newDirection;
    }

    public Direction turnRight(Direction currentDirection, boolean sharpTurn)
    {
        Direction newDirection = currentDirection;

        //
        if (currentDirection == Direction.NW) {
            newDirection = Direction.N;
        } else {
            newDirection = Direction.values()[currentDirection.getId() + 1];
        }

        //recursive, will turn once more to make a sharp turn
        if (sharpTurn) {
            newDirection = turnRight(newDirection, false);
        }

        return newDirection;
    }

    //note that this method does not take the bounds of the border into consideration
    public Coordinate moveForward(Ship ship)
    {
        int currentX = ship.getCoordinate().getX();
        int currentY = ship.getCoordinate().getY();

        Coordinate newPos = new Coordinate(currentX, currentY);

        switch (ship.getDirection())
        {
            case N:
                newPos.setY(currentY + 1);
                break;
            case S:
                newPos.setY(currentY - 1);
                break;
            case NE:
                newPos.setY(currentY + 1);
                newPos.setX(currentX + 1);
                break;
            case SE:
                newPos.setY(currentY - 1);
                newPos.setX(currentX + 1);
                break;
            case SW:
                newPos.setY(currentY - 1);
                newPos.setX(currentX - 1);
                break;
            case NW:
                newPos.setY(currentY + 1);
                newPos.setX(currentX - 1);
                break;
        }

        return newPos;
    }
}
