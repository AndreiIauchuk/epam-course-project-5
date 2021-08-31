package by.epamtc.iovchuk.parser.SAX;

import by.epamtc.iovchuk.entity.Device;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DeviceXML_SAXHandler extends DefaultHandler {

    private Device current;
    private final Set<Device> devices = new HashSet<>();
    private DeviceXMLTag xmlTag;
    private DeviceXMLTag currentXmlTag;
    private final EnumSet<DeviceXMLTag> xmlTags = EnumSet.allOf(DeviceXMLTag.class);

    private static final String ELEMENT_DEVICE = "device";

    @Override
    public void startDocument() {
        System.out.println("Parsing started");
    }

    @Override
    public void startElement
            (String uri, String localName, String qName, Attributes attrs) {

        if (qName.equals(ELEMENT_DEVICE)) {
            current = new Device();
        } else {
            DeviceXMLTag temp = DeviceXMLTag.valueOf(qName.toUpperCase());
            if (xmlTags.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        if (currentXmlTag!= null) {
            switch (currentXmlTag) {
                case ID -> current.setId(Integer.parseInt(data));
                case NAME -> current.setName(Integer.parseInt(data));
                case STREET -> current.getAddress().setStreet(data);
                case CITY -> current.getAddress().setCity(data);
                case COUNTRY -> current.getAddress().setCountry(data);
                default -> throw new EnumConstantNotPresentException
                        (currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(ELEMENT_DEVICE)) {
            devices.add(current);
        }
    }

    @Override
    public void endDocument() {
        System.out.println("\nParsing ended");
    }

}