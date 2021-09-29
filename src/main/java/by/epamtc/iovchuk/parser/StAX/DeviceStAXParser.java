package by.epamtc.iovchuk.parser.StAX;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.entity.device_price.Currency;
import by.epamtc.iovchuk.entity.device_price.Price;
import by.epamtc.iovchuk.entity.device_type.ComponentGroup;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import by.epamtc.iovchuk.entity.device_type.Port;
import by.epamtc.iovchuk.exception.XMLParserException;
import by.epamtc.iovchuk.exception.XMLTagNotSupportedException;
import by.epamtc.iovchuk.parser.CustomXMLParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static by.epamtc.iovchuk.utils.DeviceTag.COMPONENT_GROUP;
import static by.epamtc.iovchuk.utils.DeviceTag.COOLER_PRESENCE;
import static by.epamtc.iovchuk.utils.DeviceTag.CRITICAL;
import static by.epamtc.iovchuk.utils.DeviceTag.ENERGY_CONSUMPTION;
import static by.epamtc.iovchuk.utils.DeviceTag.ID;
import static by.epamtc.iovchuk.utils.DeviceTag.NAME;
import static by.epamtc.iovchuk.utils.DeviceTag.ORIGIN;
import static by.epamtc.iovchuk.utils.DeviceTag.PERIPHERAL;
import static by.epamtc.iovchuk.utils.DeviceTag.PORT;
import static by.epamtc.iovchuk.utils.DeviceTag.PRICE;
import static by.epamtc.iovchuk.utils.DeviceTag.TYPE;

public class DeviceStAXParser implements CustomXMLParser<Device> {

    private final Set<Device> devices = new TreeSet<>();

    private final XMLStreamReader reader;
    private Device current;

    public DeviceStAXParser(String filepath) throws XMLParserException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            reader = xmlInputFactory.createXMLStreamReader(new FileInputStream(filepath));
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new XMLParserException(e.getMessage(), e);
        }
    }

    @Override
    public void parse() throws XMLParserException {
        try {
            readRoot();
            readDevices();
            closeReader();
        } catch (XMLStreamException e) {
            throw new XMLParserException(e.getMessage(), e);
        }
    }

    @Override
    public Iterator<Device> iterator() {
        return devices.iterator();
    }

    private void readRoot() throws XMLStreamException {
        next();
    }

    private void readDevices() throws XMLStreamException {
        while (hasNext()) {
            next();

            if (getEventType() == XMLEvent.START_ELEMENT) {

                if (!getLocalName().equals("device")) {
                    throw new XMLTagNotSupportedException();
                }

                current = new Device();
                readDevice();
                devices.add(current);
            }
        }
    }

    private void readDevice() throws XMLStreamException {
        while (hasNext()) {
            next();

            if (getEventType() == XMLStreamReader.END_ELEMENT
                    && getLocalName().equals("device")) {
                return;
            }

            if (getEventType() == XMLStreamReader.START_ELEMENT) {
                switch (getLocalName()) {
                    case ID:
                        current.setId(Integer.parseInt(getElementText().substring(3)));
                        break;
                    case NAME:
                        current.setName(getElementText());
                        break;
                    case ORIGIN:
                        current.setOrigin(getElementText());
                        break;
                    case PRICE:
                        readPrice();
                        break;
                    case TYPE:
                        readType();
                        break;
                    case CRITICAL:
                        current.setCritical(Boolean.parseBoolean(getElementText()));
                        break;
                    default:
                        throw new XMLTagNotSupportedException();
                }
            }
        }
    }

    private void readPrice() throws XMLStreamException {
        Price price = new Price();
        if (checkIfAttrExists()) {
            Currency currency = Currency.valueOf(getAttributeValue(0));
            price.setCurrency(currency);
        }
        price.setValue(Double.parseDouble(getElementText()));

        current.setPrice(price);
    }

    private void readType() throws XMLStreamException {
        DeviceType deviceType = new DeviceType();

        while (hasNext()) {
            next();

            if (getEventType() == XMLStreamReader.END_ELEMENT
                    && getLocalName().equals("type")) {
                break;
            }

            if (getEventType() == XMLStreamReader.START_ELEMENT) {
                switch (getLocalName()) {
                    case PERIPHERAL:
                        deviceType.setPeripheral(Boolean.parseBoolean(getElementText()));
                        break;
                    case ENERGY_CONSUMPTION:
                        if (checkIfAttrExists()) {
                            deviceType.setBatteryCharged
                                    (Boolean.parseBoolean(getAttributeValue(0)));
                        }
                        deviceType.setEnergyConsumption(getElementText());
                        break;
                    case COOLER_PRESENCE:
                        deviceType.setCoolerPresence(Boolean.parseBoolean(getElementText()));
                        break;
                    case COMPONENT_GROUP:
                        deviceType.setComponentGroup(ComponentGroup.fromString(getElementText()));
                        break;
                    case PORT:
                        readPorts(deviceType);
                        break;
                    default:
                        throw new XMLTagNotSupportedException();
                }
            }
        }

        current.setDeviceType(deviceType);
    }

    private void readPorts(DeviceType deviceType) throws XMLStreamException {
        if (checkIfAttrExists()) {
            deviceType.setBluetoothConnectivity
                    (Boolean.parseBoolean(getAttributeValue(0)));
        }

        String portElemText = reader.getElementText();
        if (portElemText.isEmpty()) {
            return;
        }
        Set<Port> ports = new TreeSet<>();
        String[] portsStr = portElemText.split(" ");
        for (String portStr : portsStr) {
            ports.add(Port.valueOf(portStr));
        }

        deviceType.setPorts(ports);
    }

    private boolean hasNext() throws XMLStreamException {
        return reader.hasNext();
    }

    private int next() throws XMLStreamException {
        return reader.next();
    }

    private int getEventType() {
        return reader.getEventType();
    }

    private String getLocalName() {
        return reader.getLocalName();
    }

    private String getElementText() throws XMLStreamException {
        return reader.getElementText();
    }

    private String getAttributeValue(int index) {
        return reader.getAttributeValue(index);
    }

    private boolean checkIfAttrExists() {
        return reader.getAttributeCount() != 0;
    }

    private void closeReader() throws XMLStreamException {
        reader.close();
    }

}