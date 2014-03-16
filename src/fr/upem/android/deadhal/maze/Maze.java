package fr.upem.android.deadhal.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing the maze
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class Maze
{
    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
	private ConcurrentHashMap<String, Room> rooms;

	/**
	 * Class' constructor
	 * 
	 * @param name The name of the maze
	 */
	public Maze(String name)
	{
	    this.setName(name);
		this.rooms = new ConcurrentHashMap<String, Room>();
	}

	/**
	 * Class' constructor
	 */
	public Maze()
	{
	    this(null);
	}

	/**
	 * Method which allows to clean the maze in order to be reallocated for a new maze
	 * It clean id, the name and allocate a new ConcurrentHashMap
	 */
	public void clean()
	{
	    this.id    = null;
	    this.name  = null;
	    this.rooms = new ConcurrentHashMap<String, Room>();
	}

	/**
	 * @return The rooms of the current maze
	 */
	public ConcurrentHashMap<String, Room> getRooms()
	{
	    return this.rooms;
	}

	/**
	 * Set the id and the name of the current maze from the passed name
	 * 
	 * @param name The name of the maze
	 * 
	 * @return The current maze
	 */
    public Maze setName(String name)
    {
        this.id     = name;
        this.name   = name;
        
        return this;
    }

    /**
     * @return The id of the maze
     */
	public String getId()
	{
	    return this.id;
	}

    /**
     * @return The name of the maze
     */
	public String getName()
	{
	    return this.name;
	}

	/**
	 * Add a room to the current maze
     *
	 * @param room The room to add
	 * 
	 * @return The current maze
	 */
	public Maze addRoom(Room room)
	{
	    this.rooms.putIfAbsent(room.getId(), room);

	    return this;
	}

	/**
	 * Remove a room from the current maze
	 * 
	 * @param room The room to remove
     *
	 * @return The current maze
	 */
	public Maze removeRoom(Room room)
	{
	    this.rooms.remove(room);

	    return this;
	}

	/**
	 * @return A ascendant ordered representation of the rooms by keys (room's id) and values (room's name) in the maze
	 */
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

    /**
     * @return A list of the room's id in the maze
     */
    public ArrayList<String> getRoomIds()
    {
        ArrayList<String> roomIds = new ArrayList<String>();
        for (Room room : this.getRooms().values())
            roomIds.add(room.getId());

        Collections.sort(roomIds);
        
        return roomIds;
    }

    /**
     * Return a room (if existing in the current maze) by its id
     * 
     * @param id The id of the room
     * 
     * @return The room to retrieve
     */
    public Room getRoomById(String id)
    {
        return this.rooms.get(id);
    }
}
