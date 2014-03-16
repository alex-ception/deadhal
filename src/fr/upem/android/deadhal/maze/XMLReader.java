package fr.upem.android.deadhal.maze;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.upem.android.deadhal.utils.MazeBuilder;

import android.graphics.Point;

/**
 * Class creating a Maze from its XML representation
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class XMLReader
{
    private final Maze maze;
    private final Document document;

    /**
     * Class constructor, directly building the Maze after cleaning it
     * 
     * @param maze The maze object to fill
     * @param xmlString A string representation of the XML maze
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public XMLReader(Maze maze, String xmlString) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder         = factory.newDocumentBuilder();
        this.maze                       = maze;
        this.document                   = builder.parse(new InputSource(new StringReader(xmlString)));

        this.maze.clean();
        this.build();
    }

    /**
     * Handles the logic behind the Maze construction
     * It builds the maze from the different XML nodes
     */
    public void build()
    {
        NodeList rooms = this.document.getElementsByTagName(XMLRW.ID_ROOM);
        this.maze.setName(this.document.getDocumentElement().getAttribute(XMLRW.ATTR_ROOM_NAME));

        for (int i = 0 ; i < rooms.getLength() ; i++)
            this.buildRoom((Element) rooms.item(i));

        for (int i = 0 ; i < rooms.getLength() ; i++)
            this.buildInputsOutputs((Element) rooms.item(i));
    }

    /**
     * Build a specific room
     * 
     * @param room The XML node representing a room
     */
    public void buildRoom(Element room)
    {
        String id       = room.getAttribute(XMLRW.ATTR_ROOM_ID);
        String name     = room.getAttribute(XMLRW.ATTR_ROOM_NAME);
        int x           = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_X));
        int y           = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_Y));
        int width       = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_WIDTH));
        int height      = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_HEIGHT));
        float rotation  = Float.parseFloat(room.getAttribute(XMLRW.ATTR_ROOM_ROTATION));
        Room roomObject = MazeBuilder.newRoom(id, name, new Point(x, y), new Point(width, height), rotation);

        this.maze.addRoom(roomObject);
    }

    /**
     * Builds the inputs/outputs of a specific room
     * 
     * @param room The XML node representing a room
     */
    public void buildInputsOutputs(Element room)
    {
        Room roomObject     = this.maze.getRoomById(room.getAttribute(XMLRW.ATTR_ROOM_ID));
        NodeList outputs    = room.getElementsByTagName(XMLRW.ID_OUTPUT);

        for (int i = 0 ; i < outputs.getLength() ; i++) {
            Element output  = (Element) outputs.item(i);
            Room roomTo     = this.maze.getRoomById(output.getAttribute(XMLRW.ID_ROOM));
            String from     = output.getAttribute(XMLRW.ATTR_IO_FROM);
            String to       = output.getAttribute(XMLRW.ATTR_IO_TO);
            MazeBuilder.newIo(
                roomObject,
                Direction.getDirectionToInt(from),
                roomTo,
                Direction.getDirectionToInt(to),
                false
            );
        }
    }

    /**
     * @return The builded maze
     */
    public Maze getMaze()
    {
        return this.maze;
    }
}
