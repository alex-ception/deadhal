package fr.upem.android.deadhal.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class Maze
{
    private String id;
    private String name;
	private ConcurrentHashMap<String, Room> rooms;
	
	public Maze(String name)
	{
	    this.setName(name);
		this.rooms = new ConcurrentHashMap<String, Room>();
	}

	public Maze()
	{
	    this(null);
	}

	public void clean()
	{
	    this.id    = null;
	    this.name  = null;
	    this.rooms = new ConcurrentHashMap<String, Room>();
	}
	
	public ConcurrentHashMap<String, Room> getRooms()
	{
	    return this.rooms;
	}

    public Maze setName(String name)
    {
        this.id     = name;
        this.name   = name;
        
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

    public TreeMap<String, String> getRoomsByIdAndName()
    {
        final ConcurrentHashMap<String, String> rooms   = new ConcurrentHashMap<String, String>();
        TreeMap<String, String> sortedRooms             = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2)
            {
                return rooms.get(o1).compareTo(rooms.get(o2));
            }
        });

        for (Room room : this.getRooms().values())
            rooms.put(room.getId(), room.getName());
        sortedRooms.putAll(rooms);

        return sortedRooms;
    }

    public ArrayList<String> getRoomIds()
    {
        ArrayList<String> roomIds = new ArrayList<String>();
        for (Room room : this.getRooms().values())
            roomIds.add(room.getId());

        Collections.sort(roomIds);
        
        return roomIds;
    }

    public Room getRoomById(String id)
    {
        return this.rooms.get(id);
    }
}
