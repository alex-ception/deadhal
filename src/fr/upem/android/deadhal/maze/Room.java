package fr.upem.android.deadhal.maze;

public class Room
{
    /**
     * 
     */
    private final String id;
    
    /**
     * 
     */
    private String name;
    
    /**
     * 
     */
    private int x;
    
    /**
     * 
     */
    private int y;
    
    /**
     * 
     */
    private int width;
    
    /**
     * 
     */
    private int height;
    
    /**
     * 
     */
    private int rotation;
    
    /**
     * 
     */
    private final Input inputs;
    
    /**
     * 
     */
    private final Output outputs;
    
    public Room(String id)
    {
        this.inputs    = new Input();
        this.outputs   = new Output();
        this.id        = id;
    }
    
    public Room(String id, String name)
    {
        this(id);
        this.name = name;
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
    
    public int getRotation()
    {
        return this.rotation;
    }
    
    public Room setName(String name)
    {
        this.name = name;
    
        return this;
    }
    
    
    public Room setX(int x)
    {
        this.x = x;
    
        return this;
    }
    
    public Room setY(int y)
    {
        this.y = y;
    
        return this;
    }
    
    public Room setWidth(int width)
    {
        this.width = width;
    
        return this;
    }
    
    public Room setHeight(int height)
    {
        this.height = height;
    
        return this;
    }
    
    public Room setRotation(int rotation)
    {
        this.rotation = rotation;
    
        return this;
    }
    
    public Input getInputs()
    {
        return this.inputs;
    }
    
    public Output getOutputs()
    {
        return this.outputs;
    }
}
