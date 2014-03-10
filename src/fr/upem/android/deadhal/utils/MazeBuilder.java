package fr.upem.android.deadhal.utils;

import android.graphics.Point;
import fr.upem.android.deadhal.maze.Room;

abstract public class MazeBuilder
{
    public static Room newRoom(String name)
    {
        return MazeBuilder.newRoom(name, new Point(0, 0), new Point(100, 100), 0);
    }

    public static Room newRoom(String name, Point position, Point size, float rotation)
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
