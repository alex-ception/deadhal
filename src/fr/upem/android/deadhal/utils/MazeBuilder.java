package fr.upem.android.deadhal.utils;

import android.graphics.Point;
import fr.upem.android.deadhal.maze.Room;

abstract public class MazeBuilder
{
    public static Room newRoom(String name)
    {
        return MazeBuilder.newRoom(name, new Point(350, 500), new Point(300, 300), 0);
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
