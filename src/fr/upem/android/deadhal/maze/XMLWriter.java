package fr.upem.android.deadhal.maze;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter
{
    public static String ID_ROOT        = "maze";
    public static String ID_ROOMS       = "rooms";
    public static String ID_ROOM        = "room";
    public static String ID_IOS         = "ios";
    public static String ID_IO          = "io";
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

    private final Maze maze;
    private final Document document;

    public XMLWriter(Maze maze) throws ParserConfigurationException
    {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder         = factory.newDocumentBuilder();
        this.maze                       = maze;
        this.document                   = builder.newDocument();

        this.build();
    }

    public void build()
    {
        Element root = this.document.createElement(XMLWriter.ID_ROOT);

        root.setAttribute(XMLWriter.ATTR_ROOT_ID, this.maze.getId());
        root.setAttribute(XMLWriter.ATTR_ROOT_NAME, this.maze.getName());

        root.appendChild(this.buildRooms());
        root.appendChild(this.buildIos());
        root.appendChild(this.buildInterests());

        this.document.appendChild(root);
    }

    public Element buildRooms()
    {
        Element rooms = this.document.createElement(XMLWriter.ID_ROOMS);
        
        for (Room room : this.maze.getRooms().values()) {
            Element roomElement = this.document.createElement(XMLWriter.ID_ROOM);
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_ID, room.getId());
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_NAME, room.getName());
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_X, "" + room.getX());
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_Y, "" + room.getY());
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_WIDTH, "" + room.getWidth());
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_HEIGHT, "" + room.getHeight());
            roomElement.setAttribute(XMLWriter.ATTR_ROOM_ROTATION, "" + room.getRotation());
            rooms.appendChild(roomElement);
        }

        return rooms;
    }

    public Element buildIos()
    {
        Element ios = this.document.createElement(XMLWriter.ID_IOS);

        return ios;
    }

    public Element buildInterests()
    {
        Element interests = this.document.createElement(XMLWriter.ID_INTERESTS);

        return interests;
    }

    public String getFileName()
    {
        return this.maze.getId() + ".xml";
    }

    public byte[] getContent() throws TransformerException
    {
        TransformerFactory factory  = TransformerFactory.newInstance();
        Transformer transformer     = factory.newTransformer();
        StringWriter writer         = new StringWriter();

        transformer.transform(new DOMSource(this.document), new StreamResult(writer));

        return writer.getBuffer().toString().getBytes();
    }
}
