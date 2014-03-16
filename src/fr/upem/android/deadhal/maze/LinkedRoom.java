package fr.upem.android.deadhal.maze;

import android.graphics.Point;

/**
 * A class representing a linked room
 * It wraps a Room into a LinkedRoom to have the cardinal direction of the IO on the wrapped room
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class LinkedRoom
{
    /**
     * 
     */
    private final int direction;

    /**
     * 
     */
    private final Room room;

    /**
     * Class constructor
     * 
     * @param direction The direction of the IO of the wrapped room
     * @param room The room to wrap
     */
    public LinkedRoom(int direction, Room room)
    {
        this.direction  = direction;
        this.room       = room;
    }

    /**
     * Return a Point corresponding to the point where the arrow ends or starts
     * Its used for graphic rendering only
     * 
     * @return The point
     */
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

    /**
     * @return The wrapped room
     */
    public Room getRoom()
    {
        return this.room;
    }

    /**
     * @see fr.upem.android.deadhal.maze.Direction#getDirectionToInt(String)
     * @return The cardinal direction as a string
     */
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
