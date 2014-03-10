package fr.upem.android.deadhal.maze;

import java.util.concurrent.ConcurrentHashMap;

public class Maze
{
    private String id;
    private String name;
	private ConcurrentHashMap<String, Room> rooms;
	
	public Maze()
	{
		this.rooms = new ConcurrentHashMap<String, Room>();
	}

	public ConcurrentHashMap<String, Room> getRooms()
	{
	    return this.rooms;
	}

	public Maze setName(String name)
	{
	    this.id    = name;
	    this.name  = name;

	    return this;
	}

	public String getId()
	{
	    return this.id;
	}

	public String getName()
	{
	    return this.name;
	}

	public Maze addRoom(Room room)
	{
	    this.rooms.putIfAbsent(room.getId(), room);

	    return this;
	}

	public Maze removeRoom(Room room)
	{
	    this.rooms.remove(room);

	    return this;
	}
}
