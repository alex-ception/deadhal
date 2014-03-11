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

public class XMLReader
{
    private final Maze maze;
    private final Document document;

    public XMLReader(Maze maze, String xmlString) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder         = factory.newDocumentBuilder();
        this.maze                       = maze;
        this.document                   = builder.parse(new InputSource(new StringReader(xmlString)));

        this.build();
    }

    public void build()
    {
        NodeList rooms = this.document.getElementsByTagName(XMLRW.ID_ROOM);
        for (int i = 0 ; i < rooms.getLength() ; i++) {
            this.buildRoom((Element) rooms.item(i));
        }
    }

    public void buildRoom(Element room)
    {
        String id       = room.getAttribute(XMLRW.ATTR_ROOM_ID);
        String name     = room.getAttribute(XMLRW.ATTR_ROOM_NAME);
        int x           = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_X));
        int y           = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_Y));
        int width       = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_WIDTH));
        int height      = Integer.parseInt(room.getAttribute(XMLRW.ATTR_ROOM_HEIGHT));
        float rotation  = Float.parseFloat(room.getAttribute(XMLRW.ATTR_ROOM_ROTATION));
        this.maze.addRoom(MazeBuilder.newRoom(id, name, new Point(x, y), new Point(width, height), rotation));
    }

    public Maze getMaze()
    {
        return this.maze;
    }
}
