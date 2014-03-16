package fr.upem.android.deadhal.maze;

/**
 * Class handling constant names for the XML representation of the maze
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class XMLRW
{
    public static String ID_ROOT        = "maze";
    public static String ID_ROOMS       = "rooms";
    public static String ID_ROOM        = "room";
    public static String ID_INPUTS      = "inputs";
    public static String ID_INPUT       = "input";
    public static String ID_OUTPUTS     = "outputs";
    public static String ID_OUTPUT      = "output";
    public static String ID_INTERESTS   = "interests";
    public static String ID_INTEREST    = "interest";
    
    public static String ATTR_ROOT_ID   = "id";
    public static String ATTR_ROOT_NAME = "name";

    public static String ATTR_ROOM_ID       = "id";
    public static String ATTR_ROOM_NAME     = "name";
    public static String ATTR_ROOM_X        = "x";
    public static String ATTR_ROOM_Y        = "y";
    public static String ATTR_ROOM_WIDTH    = "width";
    public static String ATTR_ROOM_HEIGHT   = "height";
    public static String ATTR_ROOM_ROTATION = "rotation";

    public static String ATTR_INTEREST_ID   = "id";
    public static String ATTR_INTEREST_NAME = "name";
    public static String ATTR_INTEREST_ROOM = "room";

    public static String ATTR_IO_FROM       = "from";
    public static String ATTR_IO_TO         = "to";
}
