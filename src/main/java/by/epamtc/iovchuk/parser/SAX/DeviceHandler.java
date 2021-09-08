package by.epamtc.iovchuk.parser.SAX;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.entity.device_info.Currency;
import by.epamtc.iovchuk.entity.device_info.Price;
import by.epamtc.iovchuk.entity.device_type.ComponentGroup;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import by.epamtc.iovchuk.parser.DeviceTag;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DeviceHandler extends DefaultHandler {

    private static final String ELEMENT_DEVICE = "device";

    private final EnumSet<DeviceTag> xmlTags =
            EnumSet.range(DeviceTag.ID, DeviceTag.CRITICAL);
    private final Set<Device> devices = new HashSet<>();
    private DeviceTag currentXmlTag;
    private Device current;


    @Override
    public void startDocument() {
        System.out.println("Parsing started");
    }

    @Override
    public void startElement
            (String uri, String localName, String qName, Attributes attrs) {

        final String ELEMENT_PRICE = "price";
        final String ELEMENT_ENERGY_CONSUMPTION = "energy_consumption";
        final String ELEMENT_PORT = "port";

        switch (qName) {
            case ELEMENT_DEVICE:
                current = new Device(new Price(), new DeviceType());
                break;
            case ELEMENT_PRICE:
                if (attrs.getLength() != 0) {
                    current.getPrice().setCurrency
                            (Currency.valueOf(attrs.getValue(0).toUpperCase()));
                }
                break;
            case ELEMENT_ENERGY_CONSUMPTION:
                if (attrs.getLength() != 0) {
                    current.getDeviceType().setBatteryCharged
                            (Boolean.parseBoolean(attrs.getValue(0)));
                }
                break;
            case ELEMENT_PORT:
                if (attrs.getLength() != 0) {
                    current.getDeviceType().setBluetoothConnectivity
                            (Boolean.parseBoolean(attrs.getValue(0)));
                }
                break;
            default:
                DeviceTag temp = DeviceTag.valueOf(qName.toUpperCase());
                if (xmlTags.contains(temp)) {
                    currentXmlTag = temp;
                }
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();

        if (currentXmlTag != null) {
            switch (currentXmlTag) {
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
                    String[] ports = data.split(" ");
                    current.getDeviceType().setPorts
                            (new HashSet(Arrays.asList(ports)));
                    break;
                }
                case CRITICAL: {
                    current.setCritical(Boolean.parseBoolean(data));
                    break;
                }
                default:
                    throw new EnumConstantNotPresentException
                            (currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(ELEMENT_DEVICE)) {
            devices.add(current);
        }
    }

    @Override
    public void endDocument() {
        System.out.println(devices);
    }

}