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

/**
 * Class creating a XML representation of the Maze based on its object
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class XMLWriter
{
    /**
     * 
     */
    private final Maze maze;

    /**
     * 
     */
    private final Document document;

    /**
     * Class constructor, directly building the XML representation
     * 
     * @param maze The maze to represent as a XML string
     * @throws ParserConfigurationException
     */
    public XMLWriter(Maze maze) throws ParserConfigurationException
    {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder         = factory.newDocumentBuilder();
        this.maze                       = maze;
        this.document                   = builder.newDocument();

        this.build();
    }

    /**
     * Handles the logic behind the XML construction
     * It builds the maze from the different XML nodes
     */
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

    /**
     * Build a specific room representation
     * 
     * @param room The room to represent
     * @param roomsNode The root node for rooms
     */
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

    /**
     * Builds an Input or Output based on parameters given
     * 
     * @param IOType A string saying if it's an Input or an Output
     * @param direction The cardinal direction of the IO on the room
     * @param room The linked room to link to
     * @return An element representing the IO created
     */
    public Element buildDirectedIO(String IOType, String direction, LinkedRoom room)
    {
        Element directedIOElement = this.document.createElement(IOType);

        directedIOElement.setAttribute(XMLRW.ID_ROOM, room.getRoom().getId());
        directedIOElement.setAttribute(XMLRW.ATTR_IO_FROM, direction);
        directedIOElement.setAttribute(XMLRW.ATTR_IO_TO, room.getDirectionString());

        return directedIOElement;
    }

    /**
     * Builds the different inputs
     * 
     * @param room The room which the inputs belongs to
     * @param inputsNode The root node for the inputs
     */
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

    /**
     * Builds the different outputs
     * 
     * @param room The room which the outputs belongs to
     * @param inputsNode The root node for the outputs
     */
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

    /**
     * Build an interest
     * 
     * @param room The room which the interest belongs to
     * @param roomNode The room node
     */
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

    /**
     * @return the file name
     */
    public String getFileName()
    {
        return this.maze.getId() + ".xml";
    }

    /**
     * @return A string representation of the XML builded
     * @throws TransformerException
     */
    public String getContent() throws TransformerException
    {
        TransformerFactory factory  = TransformerFactory.newInstance();
        Transformer transformer     = factory.newTransformer();
        StringWriter writer         = new StringWriter();

        transformer.transform(new DOMSource(this.document), new StreamResult(writer));

        return writer.getBuffer().toString();
    }
}
