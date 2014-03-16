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
    private final String id;
    private final String name;

    /**
     * Class constructor
     * 
     * @param name The name of the interest
     */
    public Interest(String name)
    {
        this.id         = name;
        this.name       = name;
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

}
