package fr.upem.android.deadhal.maze;

import java.util.concurrent.ConcurrentHashMap;

public class Maze
{
	private ConcurrentHashMap<String, Room> rooms;
	
	public Maze()
	{
		this.rooms = new ConcurrentHashMap<String, Room>();
	}
}
