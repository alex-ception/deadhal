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

    public Direction remove(int direction, Room room)
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

    public Direction removeNorth(Room room)
    {
        return this.remove(Direction.NORTH, room);
    }

    public Direction removeSouth(Room room)
    {
        return this.remove(Direction.SOUTH, room);
    }

    public Direction removeWest(Room room)
    {
        return this.remove(Direction.WEST, room);
    }

    public Direction removeEast(Room room)
    {
        return this.remove(Direction.EAST, room);
    }

    public ConcurrentHashMap<String, Room> get(int direction, Room room)
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

    public ConcurrentHashMap<String, Room> getNorth()
    {
        return this.north;
    }

    public ConcurrentHashMap<String, Room> getSouth()
    {
        return this.south;
    }

    public ConcurrentHashMap<String, Room> getWest()
    {
        return this.west;
    }

    public ConcurrentHashMap<String, Room> getEast()
    {
        return this.east;
    }
}
