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

        Element roomsNode       = this.document.createElement(XMLWriter.ID_ROOMS);
        Element interestsNode   = this.document.createElement(XMLWriter.ID_INTERESTS);

        for (Room room : this.maze.getRooms().values()) {
            this.buildRoom(room, roomsNode);
            this.buildInterests(room, interestsNode);
        }

        root.appendChild(roomsNode);
        root.appendChild(interestsNode);

        this.document.appendChild(root);
    }

    public void buildRoom(Room room, Element roomsNode)
    {
        Element roomElement = this.document.createElement(XMLWriter.ID_ROOM);
        Element inputsNode  = this.document.createElement(XMLWriter.ID_INPUTS);
        Element outputsNode = this.document.createElement(XMLWriter.ID_OUTPUTS);

        roomElement.setAttribute(XMLWriter.ATTR_ROOM_ID, room.getId());
        roomElement.setAttribute(XMLWriter.ATTR_ROOM_NAME, room.getName());
        roomElement.setAttribute(XMLWriter.ATTR_ROOM_X, "" + room.getX());
        roomElement.setAttribute(XMLWriter.ATTR_ROOM_Y, "" + room.getY());
        roomElement.setAttribute(XMLWriter.ATTR_ROOM_WIDTH, "" + room.getWidth());
        roomElement.setAttribute(XMLWriter.ATTR_ROOM_HEIGHT, "" + room.getHeight());
        roomElement.setAttribute(XMLWriter.ATTR_ROOM_ROTATION, "" + room.getRotation());

        this.buildInputs(room, inputsNode);
        this.buildOutputs(room, inputsNode);

        roomElement.appendChild(inputsNode);
        roomElement.appendChild(outputsNode);
        roomsNode.appendChild(roomElement);
    }
    
    public void buildInputs(Room room, Element inputsNode)
    {
        for (LinkedRoom input : room.getInputs().getNorth()) {
            Element inputElement = this.document.createElement(XMLWriter.ID_INPUT);
            inputElement.setAttribute(XMLWriter.ID_ROOM, input.getRoom().getId());
            inputElement.setAttribute(XMLWriter.ATTR_IO_FROM, "north");
            inputElement.setAttribute(XMLWriter.ATTR_IO_TO, input.getDirectionString());
        }
        for (LinkedRoom input : room.getInputs().getSouth()) {
            Element inputElement = this.document.createElement(XMLWriter.ID_INPUT);
            inputElement.setAttribute(XMLWriter.ID_ROOM, input.getRoom().getId());
            inputElement.setAttribute(XMLWriter.ATTR_IO_FROM, "south");
            inputElement.setAttribute(XMLWriter.ATTR_IO_TO, input.getDirectionString());
        }
        for (LinkedRoom input : room.getInputs().getWest()) {
            Element inputElement = this.document.createElement(XMLWriter.ID_INPUT);
            inputElement.setAttribute(XMLWriter.ID_ROOM, input.getRoom().getId());
            inputElement.setAttribute(XMLWriter.ATTR_IO_FROM, "west");
            inputElement.setAttribute(XMLWriter.ATTR_IO_TO, input.getDirectionString());
        }
        for (LinkedRoom input : room.getInputs().getEast()) {
            Element inputElement = this.document.createElement(XMLWriter.ID_INPUT);
            inputElement.setAttribute(XMLWriter.ID_ROOM, input.getRoom().getId());
            inputElement.setAttribute(XMLWriter.ATTR_IO_FROM, "east");
            inputElement.setAttribute(XMLWriter.ATTR_IO_TO, input.getDirectionString());
        }
    }

    public void buildOutputs(Room room, Element outputsNode)
    {
    }

    public void buildInterests(Room room, Element interestsNode)
    {
        Element interestElement = this.document.createElement(XMLWriter.ID_INTEREST);
        interestElement.setAttribute(XMLWriter.ATTR_INTEREST_ID, room.getInterest().getId());
        interestElement.setAttribute(XMLWriter.ATTR_INTEREST_NAME, room.getInterest().getName());
        interestElement.setAttribute(XMLWriter.ATTR_INTEREST_ROOM, room.getId());
        interestsNode.appendChild(interestElement);
    }

    public String getFileName()
    {
        return this.maze.getId() + ".xml";
    }

    public String getContent() throws TransformerException
    {
        TransformerFactory factory  = TransformerFactory.newInstance();
        Transformer transformer     = factory.newTransformer();
        StringWriter writer         = new StringWriter();

        transformer.transform(new DOMSource(this.document), new StreamResult(writer));

        return writer.getBuffer().toString();
    }
}
