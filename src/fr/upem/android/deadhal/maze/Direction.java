package fr.upem.android.deadhal.maze;

import java.util.ArrayList;

/**
 * A class representing the different inputs or outputs in a room
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public abstract class Direction
{
    /**
     * 
     */
    public static final int NORTH   = 0;

    /**
     * 
     */
    public static final int SOUTH   = 1;

    /**
     * 
     */
    public static final int WEST    = 2;

    /**
     * 
     */
    public static final int EAST    = 4;

    /**
     * 
     */
    public static final String NORTH_STR   = "north";

    /**
     * 
     */
    public static final String SOUTH_STR   = "south";

    /**
     * 
     */
    public static final String WEST_STR    = "west";

    /**
     * 
     */
    public static final String EAST_STR    = "east";

    /**
     * 
     */
    private final ArrayList<LinkedRoom> north;

    /**
     * 
     */
    private final ArrayList<LinkedRoom> south;

    /**
     * 
     */
    private final ArrayList<LinkedRoom> west;

    /**
     * 
     */
    private final ArrayList<LinkedRoom> east;

    /**
     * Returns a direction as a int based on its representation as a String
     * 
     * Note : we would have used an Enum which is proper if we had more time
     * instead of this dirty solution with integers and strings
     * 
     * @param direction The direction as a String
     * @return The direction as a int
     */
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

    /**
     * Class constructor
     * 
     * Instantiates the different lists
     */
    public Direction()
    {
        this.north  = new ArrayList<LinkedRoom>();
        this.south  = new ArrayList<LinkedRoom>();
        this.west   = new ArrayList<LinkedRoom>();
        this.east   = new ArrayList<LinkedRoom>();
    }

    /**
     * Add a room to a specific cardinal point
     * 
     * @param direction The cardinal direction where the linked room will be added
     * @param room The linked room to add
     * @return The current Direction
     */
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

    /**
     * Add a linked room to the north
     * 
     * @param room The linked room to add
     * @return The current Direction
     */
    public Direction addNorth(LinkedRoom room)
    {
        return this.add(Direction.NORTH, room);
    }

    /**
     * Add a linked room to the south
     * 
     * @param room The linked room to add
     * @return The current Direction
     */
    public Direction addSouth(LinkedRoom room)
    {
        return this.add(Direction.SOUTH, room);
    }

    /**
     * Add a linked room to the west
     * 
     * @param room The linked room to add
     * @return The current Direction
     */
    public Direction addWest(LinkedRoom room)
    {
        return this.add(Direction.WEST, room);
    }

    /**
     * Add a linked room to the east
     * 
     * @param room The linked room to add
     * @return The current Direction
     */
    public Direction addEast(LinkedRoom room)
    {
        return this.add(Direction.EAST, room);
    }

    /**
     * Remove a linked room from a specific cardinal direction
     * 
     * @param direction The direction where the room should belongs
     * @param room The linked room to remove
     * @return The current Direction
     */
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

    /**
     * Remove a linked room from the north
     * 
     * @param room The linked room to remove
     * @return The current Direction
     */
    public Direction removeNorth(LinkedRoom room)
    {
        return this.remove(Direction.NORTH, room);
    }

    /**
     * Remove a linked room from the south
     * 
     * @param room The linked room to remove
     * @return The current Direction
     */
    public Direction removeSouth(LinkedRoom room)
    {
        return this.remove(Direction.SOUTH, room);
    }

    /**
     * Remove a linked room from the west
     * 
     * @param room The linked room to remove
     * @return The current Direction
     */
    public Direction removeWest(LinkedRoom room)
    {
        return this.remove(Direction.WEST, room);
    }

    /**
     * Remove a linked room from the east
     * 
     * @param room The linked room to remove
     * @return The current Direction
     */
    public Direction removeEast(LinkedRoom room)
    {
        return this.remove(Direction.EAST, room);
    }

    /**
     * Get a list of the linked rooms from the specific cardinal direction
     * 
     * @param room The room
     * @return The current Direction
     */
    public ArrayList<LinkedRoom> get(int direction)
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

    /**
     * @return North linked rooms
     */
    public ArrayList<LinkedRoom> getNorth()
    {
        return this.north;
    }

    /**
     * @return South linked rooms
     */
    public ArrayList<LinkedRoom> getSouth()
    {
        return this.south;
    }

    /**
     * @return West linked rooms
     */
    public ArrayList<LinkedRoom> getWest()
    {
        return this.west;
    }

    /**
     * @return East linked rooms
     */
    public ArrayList<LinkedRoom> getEast()
    {
        return this.east;
    }
}
