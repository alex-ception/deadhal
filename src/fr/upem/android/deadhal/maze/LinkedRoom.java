package fr.upem.android.deadhal.maze;

import android.graphics.Point;

public class LinkedRoom
{
    private final int direction;
    private final Room room;

    public LinkedRoom(int direction, Room room)
    {
        this.direction  = direction;
        this.room       = room;
    }
    
    public Point getEndingPoint()
    {
        switch (direction) {
            case Direction.NORTH:
                return new Point((int)room.getX(),(int) room.getYTop());
            case Direction.SOUTH:
                return new Point((int)room.getX(),(int) room.getYBottom());
            case Direction.WEST:
                return new Point((int)room.getXLeft(),(int) room.getY());
            case Direction.EAST:
                return new Point((int)room.getXRight(),(int) room.getY());
            default:
                throw new IllegalArgumentException("Undefined direction");
        }
    }

    public Room getRoom()
    {
        return this.room;
    }

    public String getDirectionString()
    {
        switch (direction) {
            case Direction.NORTH:
                return Direction.NORTH_STR;
            case Direction.SOUTH:
                return Direction.SOUTH_STR;
            case Direction.WEST:
                return Direction.WEST_STR;
            case Direction.EAST:
                return Direction.EAST_STR;
            default:
                throw new IllegalArgumentException("Undefined direction");
        }
    }
}
