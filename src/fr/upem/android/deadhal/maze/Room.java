package fr.upem.android.deadhal.maze;

import java.util.UUID;

import fr.upem.android.deadhal.utils.MazeBuilder;

/**
 * A class representing a room
 *
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class Room
{
	private final String id;
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private float rotation;
	private final Input inputs;
	private final Output outputs;
	private Interest interest;
	private boolean occuped;

	/**
	 * Class constructor
	 * 
	 * @param id The id of the room
	 * @param name The name of the room
	 * @throws RuntimeException
	 */
	public Room(String id, String name) throws RuntimeException
	{
		MazeBuilder.getInstance().referenceRoomName(name);

		this.inputs = new Input();
		this.outputs = new Output();
		this.id = (id == null) ? UUID.randomUUID().toString() : id;
		this.name = name;
		this.occuped = false;
	}

	/**
	 * @return The id of the room
	 */
	public String getId()
	{
		return this.id;
	}

    /**
     * @return The name of the room
     */
	public String getName()
	{
		return this.name;
	}

    /**
     * @return The X position of the room
     */
	public int getX()
	{
		return this.x;
	}

    /**
     * @return The Y position of the room
     */
	public int getY()
	{
		return this.y;
	}

    /**
     * @return The width of the room
     */
	public int getWidth()
	{
		return this.width;
	}

    /**
     * @return The height of the room
     */
	public int getHeight()
	{
		return this.height;
	}

    /**
     * @return The rotation angle of the room in degrees
     */
	public float getRotation()
	{
		return this.rotation;
	}

    /**
     * @return The interest of the room, if any
     */
	public Interest getInterest()
	{
		return this.interest;
	}

	/**
	 * Sets the name of the room
	 * 
	 * @param name The name of the room
	 * @return The current Room
	 */
	public Room setName(String name)
	{
		this.name = name;

		return this;
	}

    /**
     * Sets the X position of the room
     * 
     * @param x The X position of the room
     * @return The current Room
     */
	public Room setX(int x)
	{
		this.x = x;

		return this;
	}

    /**
     * Sets the Y position of the room
     * 
     * @param y The Y position of the room
     * @return The current Room
     */
	public Room setY(int y)
	{
		this.y = y;

		return this;
	}

    /**
     * Sets the width of the room
     * 
     * @param width The width of the room
     * @return The current Room
     */
	public Room setWidth(int width)
	{
		this.width = width;

		return this;
	}

    /**
     * Sets the height of the room
     * 
     * @param height The height of the room
     * @return The current Room
     */
	public Room setHeight(int height)
	{
		this.height = height;

		return this;
	}

    /**
     * Sets the rotation angle of the room
     * 
     * @param rotation The rotation angle of the room
     * @return The current Room
     */
	public Room setRotation(float rotation)
	{
		this.rotation = rotation;

		return this;
	}

	/**
	 * Sets the interest of the room
	 * 
	 * @param interest The interest of the room
	 * @return The current Room
	 */
	public Room setInterest(Interest interest)
	{
		this.interest = interest;

		return this;
	}

	/**
	 * @return The inputs of the room
	 */
	public Input getInputs()
	{
		return this.inputs;
	}

	/**
	 * @return The outputs of the room
	 */
	public Output getOutputs()
	{
		return this.outputs;
	}

	/**
	 * @return The west position of the room
	 */
	public int getXLeft()
	{
		return getX() - (getWidth() / 2);
	}

	/**
	 * @return The east position of the room
	 */
	public int getXRight()
	{
		return getX() + (getWidth() / 2);
	}

    /**
     * @return The north position of the room
     */
	public int getYTop()
	{
		return getY() - (getHeight() / 2);
	}

    /**
     * @return The south position of the room
     */
	public int getYBottom()
	{
		return getY() + (getHeight() / 2);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Room) {
			if (this.getId() == ((Room) o).getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return a boolean specifying if the room is occupied
	 */
	public boolean isOccuped()
	{
		return occuped;
	}

	/**
	 * Sets if the room is occupied or not
	 * 
	 * @param occuped A boolean representation
	 */
	public void setOccuped(boolean occuped) 
	{
		this.occuped = occuped;
	}
}
