package fr.upem.android.deadhal.utils;

import java.util.ArrayList;

import android.graphics.Point;
import fr.upem.android.deadhal.maze.Room;

public class MazeBuilder
{
    private static MazeBuilder instance;
    private final ArrayList<String> roomNames;

    private MazeBuilder()
    {
        this.roomNames = new ArrayList<String>();
    }

    public static MazeBuilder getInstance()
    {
        if (MazeBuilder.instance == null)
            MazeBuilder.instance = new MazeBuilder();

        return MazeBuilder.instance;
    }

    public boolean isRoomNameReferenced(String name)
    {
        return this.roomNames.contains(name);
    }

    public MazeBuilder referenceRoomName(String name) throws RuntimeException
    {
        if (this.isRoomNameReferenced(name))
            throw new RuntimeException();

        this.roomNames.add(name);

        return this;
    }

    public static Room newRoom(String name)
    {
        return MazeBuilder.newRoom(name, new Point(0, 0), new Point(100, 100), 0);
    }

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

}
