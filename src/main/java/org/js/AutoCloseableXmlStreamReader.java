package org.js;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

public class AutoCloseableXmlStreamReader implements AutoCloseable {

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader xmlStreamReader;

    public AutoCloseableXmlStreamReader(InputStream inputStream) throws XMLStreamException {
        this.xmlStreamReader = XML_INPUT_FACTORY.createXMLStreamReader(inputStream);
    }

    @Override
    public void close() throws XMLStreamException {
        xmlStreamReader.close();
    }

    public void nextTag() throws XMLStreamException {
        xmlStreamReader.nextTag();
    }

    public void skipToTag(final String tagName) throws XMLStreamException {
        while (xmlStreamReader.getEventType() != XMLStreamConstants.START_ELEMENT || !getLocalName().equals(tagName)) {
            if (!xmlStreamReader.hasNext()) {
                throw new XMLStreamException("Couldn't find the " + tagName + " tag!");
            }
            xmlStreamReader.next();
        }
    }

    public String getLocalName() {
        return xmlStreamReader.getLocalName();
    }

    public String getTextContent() throws XMLStreamException {
        try {
            xmlStreamReader.next();
        } catch (XMLStreamException e) {
            throw new XMLStreamException("Couldn't find the xml tag content");
        }
        return xmlStreamReader.getText();
    }

}