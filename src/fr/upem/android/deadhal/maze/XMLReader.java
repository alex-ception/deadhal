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

import android.util.Log;

public class XMLReader
{
    private final Maze maze;
    private final Document document;

    public XMLReader(String xmlString) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder         = factory.newDocumentBuilder();
        this.maze                       = new Maze();
        this.document                   = builder.parse(new InputSource(new StringReader(xmlString)));

        this.build();
    }

    public void build()
    {
        NodeList rooms = this.document.getElementsByTagName(XMLRW.ID_ROOM);
        for (int i = 0 ; i < rooms.getLength() ; i++) {
            Log.e("DH", ((Element) rooms.item(i)).getAttribute(XMLRW.ATTR_ROOM_NAME));
        }
    }

    public Maze getMaze()
    {
        return this.maze;
    }
}
