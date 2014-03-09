package fr.upem.android.deadhal.maze;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Direction
{
    public static final int NORTH   = 0;
    public static final int SOUTH   = 1;
    public static final int WEST    = 2;
    public static final int EAST    = 4;

    private final ConcurrentHashMap<String, Room> north;
    private final ConcurrentHashMap<String, Room> south;
    private final ConcurrentHashMap<String, Room> west;
    private final ConcurrentHashMap<String, Room> east;

    public Direction()
    {
        this.north  = new ConcurrentHashMap<String, Room>();
        this.south  = new ConcurrentHashMap<String, Room>();
        this.west   = new ConcurrentHashMap<String, Room>();
        this.east   = new ConcurrentHashMap<String, Room>();
    }

    public Direction add(int direction, Room room)
    {
        switch (direction) {
            case Direction.NORTH:
                this.north.putIfAbsent(room.getId(), room);
                break;
            case Direction.SOUTH:
                this.south.putIfAbsent(room.getId(), room);
                break;
            case Direction.WEST:
                this.west.putIfAbsent(room.getId(), room);
                break;
            case Direction.EAST:
                this.east.putIfAbsent(room.getId(), room);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction " + direction);
        }

        return this;
    }
    
    public Direction addNorth(Room room)
    {
        return this.add(Direction.NORTH, room);
    }

    public Direction addSouth(Room room)
    {
        return this.add(Direction.SOUTH, room);
    }

    public Direction addWest(Room room)
    {
        return this.add(Direction.WEST, room);
    }

    public Direction addEast(Room room)
    {
        return this.add(Direction.EAST, room);
    }
}
