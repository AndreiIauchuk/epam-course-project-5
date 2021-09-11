package by.epamtc.iovchuk.parser.SAX;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.entity.device_price.Currency;
import by.epamtc.iovchuk.entity.device_price.Price;
import by.epamtc.iovchuk.entity.device_type.ComponentGroup;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import by.epamtc.iovchuk.entity.device_type.Port;
import by.epamtc.iovchuk.exception.XMLTagNotSupportedException;
import by.epamtc.iovchuk.parser.CustomXMLParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static by.epamtc.iovchuk.utils.DeviceTag.COMPONENT_GROUP;
import static by.epamtc.iovchuk.utils.DeviceTag.COOLER_PRESENCE;
import static by.epamtc.iovchuk.utils.DeviceTag.CRITICAL;
import static by.epamtc.iovchuk.utils.DeviceTag.DEVICE;
import static by.epamtc.iovchuk.utils.DeviceTag.DEVICES;
import static by.epamtc.iovchuk.utils.DeviceTag.ENERGY_CONSUMPTION;
import static by.epamtc.iovchuk.utils.DeviceTag.ID;
import static by.epamtc.iovchuk.utils.DeviceTag.NAME;
import static by.epamtc.iovchuk.utils.DeviceTag.ORIGIN;
import static by.epamtc.iovchuk.utils.DeviceTag.PERIPHERAL;
import static by.epamtc.iovchuk.utils.DeviceTag.PORT;
import static by.epamtc.iovchuk.utils.DeviceTag.PRICE;
import static by.epamtc.iovchuk.utils.DeviceTag.TYPE;

public class DeviceSAXParser implements CustomXMLParser<Device> {

    private final String filepath;

    private Set<Device> devices;

    public DeviceSAXParser(String _filepath) {
        filepath = _filepath;
    }

    @Override
    public void parse() {
        try {
            XMLReader reader = getXMLReader();
            reader.setContentHandler(new DeviceHandler());
            //TODO  reader.setErrorHandler(new CustomErrorHandler());
            reader.parse(filepath);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace(); //TODO кидать наверх
        }
    }

    private XMLReader getXMLReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        return parser.getXMLReader();
    }

    @Override
    public Iterator<Device> iterator() {
        return devices.iterator();
    }

    private class DeviceHandler extends DefaultHandler {

        private String currentTag;
        private Device current;

        @Override
        public void startElement
                (String uri, String localName, String qName, Attributes attrs) {

            switch (qName) {
                case DEVICES:
                    devices = new HashSet<>();
                    break;
                case DEVICE:
                    current = new Device(new Price(), new DeviceType());
                    break;
                case PRICE:
                    if (attrs.getLength() != 0) {
                        current.getPrice().setCurrency
                                (Currency.valueOf(attrs.getValue(0).toUpperCase()));
                    }
                case ID:
                case NAME:
                case ORIGIN:
                case TYPE:
                case PERIPHERAL:
                case COOLER_PRESENCE:
                case COMPONENT_GROUP:
                case CRITICAL: {
                    currentTag = qName;
                    break;
                }
                case ENERGY_CONSUMPTION:
                    currentTag = qName;
                    if (attrs.getLength() != 0) {
                        current.getDeviceType().setBatteryCharged
                                (Boolean.parseBoolean(attrs.getValue(0)));
                    }
                    break;
                case PORT:
                    currentTag = qName;
                    if (attrs.getLength() != 0) {
                        current.getDeviceType().setBluetoothConnectivity
                                (Boolean.parseBoolean(attrs.getValue(0)));
                    }
                    break;
                default:
                    throw new XMLTagNotSupportedException();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (currentTag == null) {
                return;
            }

            String data = new String(ch, start, length).trim();

            switch (currentTag) {
                case ID: {
                    current.setId(Integer.parseInt(data.substring(3)));
                    break;
                }
                case NAME: {
                    current.setName(data);
                    break;
                }
                case ORIGIN: {
                    current.setOrigin(data);
                    break;
                }
                case PRICE: {
                    current.getPrice().setValue(Double.parseDouble(data));
                    break;
                }
                case PERIPHERAL: {
                    current.getDeviceType().setPeripheral(Boolean.parseBoolean(data));
                    break;
                }
                case ENERGY_CONSUMPTION: {
                    current.getDeviceType().setEnergyConsumption(data);
                    break;
                }
                case COOLER_PRESENCE: {
                    current.getDeviceType().setCoolerPresence
                            (Boolean.parseBoolean(data));
                    break;
                }
                case COMPONENT_GROUP: {
                    current.getDeviceType().setComponentGroup
                            (ComponentGroup.fromString(data));
                    break;
                }
                case PORT: {
                    String[] portsStr = data.split(" ");
                    Set<Port> ports = new TreeSet<>();
                    for (String portStr : portsStr) {
                        ports.add(Port.valueOf(portStr));
                    }
                    current.getDeviceType().setPorts(ports);
                    break;
                }
                case CRITICAL: {
                    current.setCritical(Boolean.parseBoolean(data));
                    break;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            currentTag = null;

            if (qName.equals(DEVICE)) {
                devices.add(current);
            }
        }
    }

}