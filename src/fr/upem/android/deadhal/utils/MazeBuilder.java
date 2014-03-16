package fr.upem.android.deadhal.utils;

import java.util.ArrayList;

import android.graphics.Point;
import android.util.Log;
import fr.upem.android.deadhal.maze.LinkedRoom;
import fr.upem.android.deadhal.maze.Room;

/**
 * A pseudo-factory helping to build a Maze
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class MazeBuilder
{
    private static MazeBuilder instance;
    private final ArrayList<String> roomNames;

    /**
     * Constructor
     */
    private MazeBuilder()
    {
        this.roomNames = new ArrayList<String>();
    }

    /**
     * @return an instance of MazeBuilder
     */
    public static MazeBuilder getInstance()
    {
        if (MazeBuilder.instance == null)
            MazeBuilder.instance = new MazeBuilder();

        return MazeBuilder.instance;
    }

    /**
     * Says if a room name has already been referenced
     * 
     * @param name The name of the room
     * @return A boolean which says if the room has already been referenced or not
     */
    public boolean isRoomNameReferenced(String name)
    {
        return this.roomNames.contains(name);
    }

    /**
     * Reference a new room name
     * 
     * @param name The name of the room to reference
     * @return The current MazeBuilder
     * @throws RuntimeException
     */
    public MazeBuilder referenceRoomName(String name) throws RuntimeException
    {
        if (this.isRoomNameReferenced(name))
            throw new RuntimeException();

        this.roomNames.add(name);

        return this;
    }

    /**
     * Clear the list of rooms names
     * 
     * @return The current MazeBuilder
     */
    public MazeBuilder clean()
    {
        this.roomNames.clear();

        return this;
    }

    /**
     * Instantiates a new room
     * 
     * @param id The id of the room
     * @param name The name of the room
     * 
     * @return The new room instantiated
     */
    public static Room newRoom(String id, String name)
    {
        return MazeBuilder.newRoom(id, name, new Point(350, 500), new Point(300, 300), 0);
    }

    /**
     * Instantiates a new room
     * 
     * @param id The id of the room
     * @param name The name of the room
     * @param position The position of the room
     * @param size The size of the room
     * @param rotation The rotation of the room
     * @throws RuntimeException
     * 
     * @return The new room instantiated
     */
    public static Room newRoom(String id, String name, Point position, Point size, float rotation) throws RuntimeException
    {
        return new Room(id, name)
            .setX(position.x)
            .setY(position.y)
            .setWidth(size.x)
            .setHeight(size.y)
            .setRotation(rotation)
        ;
    }

    /**
     * Build a new IO 
     * 
     * @param from The room where the IO starts
     * @param directionFrom The direction where the IO starts
     * @param to The room where the IO ends
     * @param directionTo The direction where the IO ends
     * @param twoWay Is the IO a two-way IO
     */
    public static void newIo(Room from, int directionFrom, Room to, int directionTo, boolean twoWay)
    {
        Log.e("DH", from.getName() + "[" + directionFrom + "] => " + to.getName() + "[" + directionTo + "] twoWay(" + twoWay + ")");
        LinkedRoom outputLinkedRoom = new LinkedRoom(directionTo, to);
        LinkedRoom inputLinkedRoom  = new LinkedRoom(directionFrom, from);

        from.getOutputs().add(directionFrom, outputLinkedRoom);
        to.getInputs().add(directionTo, inputLinkedRoom);

        if (twoWay)
            MazeBuilder.newIo(to, directionTo, from, directionFrom, false);
    }
}
