package fr.upem.android.deadhal.utils;

import java.util.ArrayList;

import android.graphics.Point;
import android.util.Log;
import fr.upem.android.deadhal.maze.LinkedRoom;
import fr.upem.android.deadhal.maze.Room;

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
     * 
     * 
     * @return
     */
    public static MazeBuilder getInstance()
    {
        if (MazeBuilder.instance == null)
            MazeBuilder.instance = new MazeBuilder();

        return MazeBuilder.instance;
    }

    /**
     * 
     * 
     * @param name
     * @return
     */
    public boolean isRoomNameReferenced(String name)
    {
        return this.roomNames.contains(name);
    }

    /**
     * 
     * 
     * @param name
     * @return
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
     * 
     * 
     * @param name
     * 
     * @return The new room instantiated
     */
    public static Room newRoom(String name)
    {
        return MazeBuilder.newRoom(name, new Point(0, 0), new Point(100, 100), 0);
    }

    /**
     * 
     * 
     * @throws RuntimeException
     * 
     * @param name
     * @param position
     * @param size
     * @param rotation

     * @return The new room instantiated
     */
    public static Room newRoom(String name, Point position, Point size, float rotation) throws RuntimeException
    {
        return new Room(name)
            .setX(position.x)
            .setY(position.y)
            .setWidth(size.x)
            .setHeight(size.y)
            .setRotation(rotation)
        ;
    }

    /**
     * 
     * 
     * @param from The room where the IO starts
     * @param directionFrom
     * @param to The room where the IO ends
     * @param directionTo
     * @param twoWay
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
