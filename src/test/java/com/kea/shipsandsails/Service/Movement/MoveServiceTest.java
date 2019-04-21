package com.kea.shipsandsails.Service.Movement;

import com.kea.shipsandsails.Model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveServiceTest {

    MovementService movementService = new MovementService();

    @Test
    void turnLeft() {
        Direction directionNorth = Direction.N;
        Direction directionSouth = Direction.S;
        assertSame(movementService.turnLeft(directionNorth, false), Direction.NW);
        assertSame(movementService.turnLeft(directionNorth, true), Direction.SW);
        assertSame(movementService.turnLeft(directionSouth, false), Direction.SE);
        assertSame(movementService.turnLeft(directionSouth, true), Direction.NE);
    }

    @Test
    void turnRight() {
        Direction directionNorth = Direction.N;
        Direction directionSouth = Direction.S;
        assertSame(movementService.turnRight(directionNorth, false), Direction.NE);
        assertSame(movementService.turnRight(directionNorth, true), Direction.SE);
        assertSame(movementService.turnRight(directionSouth, false), Direction.SW);
        assertSame(movementService.turnRight(directionSouth, true), Direction.NW);
    }
}