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
        Element root = this.document.createElement(XMLRW.ID_ROOT);

        root.setAttribute(XMLRW.ATTR_ROOT_ID, this.maze.getId());
        root.setAttribute(XMLRW.ATTR_ROOT_NAME, this.maze.getName());

        Element roomsNode       = this.document.createElement(XMLRW.ID_ROOMS);
        Element interestsNode   = this.document.createElement(XMLRW.ID_INTERESTS);

        for (Room room : this.maze.getRooms().values()) {
            this.buildRoom(room, roomsNode);
        }

        root.appendChild(roomsNode);
        root.appendChild(interestsNode);

        this.document.appendChild(root);
    }

    public void buildRoom(Room room, Element roomsNode)
    {
        Element roomElement = this.document.createElement(XMLRW.ID_ROOM);
        Element inputsNode  = this.document.createElement(XMLRW.ID_INPUTS);
        Element outputsNode = this.document.createElement(XMLRW.ID_OUTPUTS);

        roomElement.setAttribute(XMLRW.ATTR_ROOM_ID, room.getId());
        roomElement.setAttribute(XMLRW.ATTR_ROOM_NAME, room.getName());
        roomElement.setAttribute(XMLRW.ATTR_ROOM_X, "" + room.getX());
        roomElement.setAttribute(XMLRW.ATTR_ROOM_Y, "" + room.getY());
        roomElement.setAttribute(XMLRW.ATTR_ROOM_WIDTH, "" + room.getWidth());
        roomElement.setAttribute(XMLRW.ATTR_ROOM_HEIGHT, "" + room.getHeight());
        roomElement.setAttribute(XMLRW.ATTR_ROOM_ROTATION, "" + room.getRotation());

        this.buildInputs(room, inputsNode);
        this.buildOutputs(room, outputsNode);
        this.buildInterest(room, roomElement);

        roomElement.appendChild(inputsNode);
        roomElement.appendChild(outputsNode);
        roomsNode.appendChild(roomElement);
    }

    public Element buildDirectedIO(String IOType, String direction, LinkedRoom room)
    {
        Element directedIOElement = this.document.createElement(IOType);

        directedIOElement.setAttribute(XMLRW.ID_ROOM, room.getRoom().getId());
        directedIOElement.setAttribute(XMLRW.ATTR_IO_FROM, direction);
        directedIOElement.setAttribute(XMLRW.ATTR_IO_TO, room.getDirectionString());

        return directedIOElement;
    }
    
    public void buildInputs(Room room, Element inputsNode)
    {
        for (LinkedRoom input : room.getInputs().getNorth())
            inputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_INPUT, Direction.NORTH_STR, input));
        for (LinkedRoom input : room.getInputs().getSouth())
            inputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_INPUT, Direction.SOUTH_STR, input));
        for (LinkedRoom input : room.getInputs().getWest())
            inputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_INPUT, Direction.WEST_STR, input));
        for (LinkedRoom input : room.getInputs().getEast())
            inputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_INPUT, Direction.EAST_STR, input));
    }

    public void buildOutputs(Room room, Element outputsNode)
    {
        for (LinkedRoom output : room.getOutputs().getNorth())
            outputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_OUTPUT, Direction.NORTH_STR, output));
        for (LinkedRoom output : room.getOutputs().getSouth())
            outputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_OUTPUT, Direction.SOUTH_STR, output));
        for (LinkedRoom output : room.getOutputs().getWest())
            outputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_OUTPUT, Direction.WEST_STR, output));
        for (LinkedRoom output : room.getOutputs().getEast())
            outputsNode.appendChild(this.buildDirectedIO(XMLRW.ID_OUTPUT, Direction.EAST_STR, output));
    }

    public void buildInterest(Room room, Element roomNode)
    {
        if (room.getInterest() == null)
            return;
        Element interestElement = this.document.createElement(XMLRW.ID_INTEREST);
        interestElement.setAttribute(XMLRW.ATTR_INTEREST_ID, room.getInterest().getId());
        interestElement.setAttribute(XMLRW.ATTR_INTEREST_NAME, room.getInterest().getName());
        interestElement.setAttribute(XMLRW.ATTR_INTEREST_ROOM, room.getId());
        roomNode.appendChild(interestElement);
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
