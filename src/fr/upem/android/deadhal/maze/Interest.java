package fr.upem.android.deadhal.maze;

public class Interest
{
    /**
     * 
     */
    private final String id;

    /**
     * 
     */
    private final String name;

    /**
     * 
     */
    private float fontSize;

    public Interest(String name)
    {
        this.id         = name;
        this.name       = name;
        this.fontSize   = 20;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public float getFontSize()
    {
        return this.fontSize;
    }

    public Interest setFontSize(float fontSize)
    {
        this.fontSize = fontSize;

        return this;
    }
}
