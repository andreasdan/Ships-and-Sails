package com.kea.shipsandsails.Model;

public enum Direction
{
    N(0),
    NE(1),
    SE(2),
    S(3),
    SW(4),
    NW(5);

    private int id;

    Direction(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
