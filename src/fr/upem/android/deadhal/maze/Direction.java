package fr.upem.android.deadhal.maze;

import java.util.ArrayList;

public abstract class Direction
{
    public static final int NORTH   = 0;
    public static final int SOUTH   = 1;
    public static final int WEST    = 2;
    public static final int EAST    = 4;

    public static final String NORTH_STR   = "north";
    public static final String SOUTH_STR   = "south";
    public static final String WEST_STR    = "west";
    public static final String EAST_STR    = "east";

    private final ArrayList<LinkedRoom> north;
    private final ArrayList<LinkedRoom> south;
    private final ArrayList<LinkedRoom> west;
    private final ArrayList<LinkedRoom> east;

    public static int getDirectionToInt(String direction)
    {
        if (direction.equalsIgnoreCase(Direction.NORTH_STR))
            return Direction.NORTH;

        if (direction.equalsIgnoreCase(Direction.SOUTH_STR))
            return Direction.SOUTH;

        if (direction.equalsIgnoreCase(Direction.WEST_STR))
            return Direction.WEST;

        return Direction.EAST;
    }
    
    public Direction()
    {
        this.north  = new ArrayList<LinkedRoom>();
        this.south  = new ArrayList<LinkedRoom>();
        this.west   = new ArrayList<LinkedRoom>();
        this.east   = new ArrayList<LinkedRoom>();
    }

    public Direction add(int direction, LinkedRoom room)
    {
        switch (direction) {
            case Direction.NORTH:
                this.north.add(room);
                break;
            case Direction.SOUTH:
                this.south.add(room);
                break;
            case Direction.WEST:
                this.west.add(room);
                break;
            case Direction.EAST:
                this.east.add(room);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction " + direction);
        }

        return this;
    }

    public Direction addNorth(LinkedRoom room)
    {
        return this.add(Direction.NORTH, room);
    }

    public Direction addSouth(LinkedRoom room)
    {
        return this.add(Direction.SOUTH, room);
    }

    public Direction addWest(LinkedRoom room)
    {
        return this.add(Direction.WEST, room);
    }

    public Direction addEast(LinkedRoom room)
    {
        return this.add(Direction.EAST, room);
    }

    public Direction remove(int direction, LinkedRoom room)
    {
        switch (direction) {
            case Direction.NORTH:
                this.north.remove(room);
                break;
            case Direction.SOUTH:
                this.south.remove(room);
                break;
            case Direction.WEST:
                this.west.remove(room);
                break;
            case Direction.EAST:
                this.east.remove(room);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction " + direction);
        }

        return this;
    }

    public Direction removeNorth(LinkedRoom room)
    {
        return this.remove(Direction.NORTH, room);
    }

    public Direction removeSouth(LinkedRoom room)
    {
        return this.remove(Direction.SOUTH, room);
    }

    public Direction removeWest(LinkedRoom room)
    {
        return this.remove(Direction.WEST, room);
    }

    public Direction removeEast(LinkedRoom room)
    {
        return this.remove(Direction.EAST, room);
    }

    public ArrayList<LinkedRoom> get(int direction, Room room)
    {
        switch (direction) {
            case Direction.NORTH:
                return this.north;
            case Direction.SOUTH:
                return this.south;
            case Direction.WEST:
                return this.west;
            case Direction.EAST:
                return this.east;
            default:
                throw new IllegalArgumentException("Invalid direction " + direction);
        }
    }

    public ArrayList<LinkedRoom> getNorth()
    {
        return this.north;
    }

    public ArrayList<LinkedRoom> getSouth()
    {
        return this.south;
    }

    public ArrayList<LinkedRoom> getWest()
    {
        return this.west;
    }

    public ArrayList<LinkedRoom> getEast()
    {
        return this.east;
    }
}
