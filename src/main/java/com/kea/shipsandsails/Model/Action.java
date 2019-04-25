package com.kea.shipsandsails.Model;

public enum Action
{
    SPEED_UP(0),
    SPEED_DOWN(1),
    RELOAD(2),
    LOAD_NEW(3),
    CHANGERIGHT(4),
    CHANGELEFT(5),
    FIRE(6),
    END_ROUND(7),
    MOVE(8);

    private int id;

    Action(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
