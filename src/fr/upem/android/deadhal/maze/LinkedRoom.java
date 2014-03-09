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
                return new Point(room.getX(), room.getYTop());
            case Direction.SOUTH:
                return new Point(room.getX(), room.getYBottom());
            case Direction.WEST:
                return new Point(room.getXLeft(), room.getY());
            case Direction.EAST:
                return new Point(room.getXRight(), room.getY());
            default:
                throw new IllegalArgumentException("Undefined direction");
        }
    }
}
