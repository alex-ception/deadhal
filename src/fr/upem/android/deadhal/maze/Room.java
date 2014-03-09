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
    private float nameFontSize;
    
    
    /**
     * 
     */
    private float InterestFontSize;
    
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
    private float rotation;
    
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
        this.nameFontSize = 25;
        this.InterestFontSize = 20;
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
    
    public float getRotation()
    {
        return this.rotation;
    }
    
    public float getNameFontSize() {
		return this.nameFontSize;
	}

	public float getInterestFontSize() {
		return this.InterestFontSize;
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
    
    public Room setRotation(float rotation)
    {
        this.rotation = rotation;
    
        return this;
    }
    
    public Room setNameFontSize(float nameFontSize) {
        this.nameFontSize = nameFontSize;
        
        return this;
    }
    
    public Room setInterestFontSize(float interestFontSize) {
        InterestFontSize = interestFontSize;
        
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
   
    public int getXLeft() {
		return getX()-(getHeight()/2);
	}

	public int getXRight() {
		return getX()+(getHeight()/2);
	}

	public int getYTop() {
		return getY()-(getWidth()/2);
	}

	public int getYBottom() {
		return getY()+(getWidth()/2);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Room){
			if (this.getId()==((Room)o).getId()&&
					this.getName()==((Room)o).getName()&&
					this.getWidth()==((Room)o).getWidth()&&
					this.getHeight()==((Room)o).getHeight()&&
					this.getX()==((Room)o).getX()&&
					this.getY()==((Room)o).getY()){
				return true;
			}
		}
		return false;
	}
	
	
}
