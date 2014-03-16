package fr.upem.android.deadhal.maze;

/**
 * A class representing an interest in a room
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
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

    /**
     * Class constructor
     * 
     * @param name The name of the interest
     */
    public Interest(String name)
    {
        this.id         = name;
        this.name       = name;
        this.fontSize   = 20;
    }

    /**
     * @return The id of the interest
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * @return The name of the interest
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return The font size (graphic rendering)
     */
    public float getFontSize()
    {
        return this.fontSize;
    }

    /**
     * Sets the font size (graphic rendering)
     * 
     * @param fontSize The font size
     * @return The current Interest
     */
    public Interest setFontSize(float fontSize)
    {
        this.fontSize = fontSize;

        return this;
    }
}
