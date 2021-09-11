package by.epamtc.iovchuk.parser.DOM;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.entity.device_price.Currency;
import by.epamtc.iovchuk.entity.device_price.Price;
import by.epamtc.iovchuk.entity.device_type.ComponentGroup;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import by.epamtc.iovchuk.entity.device_type.Port;
import by.epamtc.iovchuk.exception.XMLParserBuilderException;
import by.epamtc.iovchuk.exception.XMLParserException;
import by.epamtc.iovchuk.exception.XMLTagNotSupportedException;
import by.epamtc.iovchuk.parser.CustomXMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static by.epamtc.iovchuk.utils.DeviceTag.BATTERY_CHARGED_ATTR;
import static by.epamtc.iovchuk.utils.DeviceTag.BLUETOOTH_CONNECTIVITY_ATTR;
import static by.epamtc.iovchuk.utils.DeviceTag.COMPONENT_GROUP;
import static by.epamtc.iovchuk.utils.DeviceTag.COOLER_PRESENCE;
import static by.epamtc.iovchuk.utils.DeviceTag.CURRENCY_ATTR;
import static by.epamtc.iovchuk.utils.DeviceTag.DEVICE;
import static by.epamtc.iovchuk.utils.DeviceTag.ENERGY_CONSUMPTION;
import static by.epamtc.iovchuk.utils.DeviceTag.ID;
import static by.epamtc.iovchuk.utils.DeviceTag.NAME;
import static by.epamtc.iovchuk.utils.DeviceTag.ORIGIN;
import static by.epamtc.iovchuk.utils.DeviceTag.PERIPHERAL;
import static by.epamtc.iovchuk.utils.DeviceTag.PORT;
import static by.epamtc.iovchuk.utils.DeviceTag.PRICE;
import static by.epamtc.iovchuk.utils.DeviceTag.TYPE;

public class DeviceDOMParser implements CustomXMLParser<Device> {

    private final String filename;
    private final Set<Device> devices;
    private final DocumentBuilder docBuilder;

    private Device currentDevice;
    private Element currentDeviceElem;
    private Price currentPrice;
    private Element currentPriceElem;
    private DeviceType currentDeviceType;
    private Element currentDeviceTypeElem;

    public DeviceDOMParser(String _filename) throws XMLParserBuilderException {
        filename = _filename;
        devices = new TreeSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLParserBuilderException(e.getMessage(), e);
        }
    }

    @Override
    public void parse() throws XMLParserException {
        try {
            buildDevices(filename);
        } catch (IOException | SAXException e) {
            throw new XMLParserException(e.getMessage(), e);
        }
    }

    @Override
    public Iterator<Device> iterator() {
        return devices.iterator();
    }

    private void buildDevices(String filename) throws IOException, SAXException {
        Document document;
        document = docBuilder.parse(filename);
        Element root = document.getDocumentElement();
        NodeList devicesElem = root.getElementsByTagName(DEVICE);

        for (int i = 0; i < devicesElem.getLength(); i++) {
            currentDeviceElem = (Element) devicesElem.item(i);
            buildDevice();
            devices.add(currentDevice);
        }
    }

    private void buildDevice() {
        currentDevice = new Device();

        buildDeviceId();
        buildDeviceName();
        buildDeviceOrigin();
        buildDevicePrice();
        buildDeviceType();
        buildDeviceCritical();
    }

    private void buildDeviceId() {
        String idElemTextContent = getChildNodeTextContent(currentDeviceElem, ID);
        int id = Integer.parseInt(idElemTextContent.substring(3));
        currentDevice.setId(id);
    }

    private void buildDeviceName() {
        String nameElemTextContent = getChildNodeTextContent(currentDeviceElem, NAME);
        currentDevice.setName(nameElemTextContent);
    }

    private void buildDeviceOrigin() {
        String originElemTextContent = getChildNodeTextContent
                (currentDeviceElem, ORIGIN);
        currentDevice.setOrigin(originElemTextContent);
    }

    private void buildDevicePrice() {
        currentPrice = new Price();
        currentPriceElem = (Element) getChildNode(currentDeviceElem, PRICE);

        buildPriceCurrency();
        buildPriceValue();

        currentDevice.setPrice(currentPrice);
    }

    private void buildPriceCurrency() {
        String currentAttrTextContent = getNodeAttrTextContext
                (currentPriceElem, CURRENCY_ATTR);
        Currency currency = Currency.valueOf(currentAttrTextContent);
        currentPrice.setCurrency(currency);
    }

    private void buildPriceValue() {
        Double priceValue = Double.parseDouble(currentPriceElem.getTextContent());
        currentPrice.setValue(priceValue);
    }

    private void buildDeviceType() {
        currentDeviceType = new DeviceType();
        currentDeviceTypeElem = (Element) getChildNode(currentDeviceElem, TYPE);

        buildDeviceTypePeripheral();
        buildDeviceTypeEnergyConsumption();
        buildDeviceTypeCoolerPresence();
        buildDeviceTypeComponentGroup();
        buildDevicePort();

        currentDevice.setDeviceType(currentDeviceType);
    }

    private void buildDeviceTypePeripheral() {
        String peripheralTextContent = getChildNodeTextContent
                (currentDeviceTypeElem, PERIPHERAL);
        boolean peripheral = Boolean.parseBoolean(peripheralTextContent);
        currentDeviceType.setPeripheral(peripheral);
    }

    private void buildDeviceTypeEnergyConsumption() {
        Element energyConsumptionElem = (Element) getChildNode
                (currentDeviceTypeElem, ENERGY_CONSUMPTION);
        String batteryChargedTextContent = getNodeAttrTextContext
                (energyConsumptionElem, BATTERY_CHARGED_ATTR);
        boolean batteryCharged = Boolean.parseBoolean(batteryChargedTextContent);
        String energyConsumptionTextContent = energyConsumptionElem.getTextContent();
        currentDeviceType.setBatteryCharged(batteryCharged);
        currentDeviceType.setEnergyConsumption(energyConsumptionTextContent);
    }

    private void buildDeviceTypeCoolerPresence() {
        String coolerPresenceTextContent = getChildNodeTextContent
                (currentDeviceTypeElem, COOLER_PRESENCE);
        boolean coolerPresence = Boolean.parseBoolean(coolerPresenceTextContent);
        currentDeviceType.setCoolerPresence(coolerPresence);
    }

    private void buildDeviceTypeComponentGroup() {
        String componentGroupTextContent = getChildNodeTextContent
                (currentDeviceTypeElem, COMPONENT_GROUP);
        ComponentGroup componentGroup = ComponentGroup.fromString(componentGroupTextContent);
        currentDeviceType.setComponentGroup(componentGroup);
    }

    private void buildDevicePort() {
        Element portElem = (Element) getChildNode
                (currentDeviceTypeElem, PORT);
        String portElemTextContent = portElem.getTextContent();
        Set<Port> ports = new TreeSet<>();
        for (String port : portElemTextContent.split(" ")) {
            if (port.isEmpty()) {
                continue;
            }
            ports.add(Port.valueOf(port));
        }
        currentDeviceType.setPorts(ports);

        String bluetoothConnectivity = getNodeAttrTextContext
                (portElem, BLUETOOTH_CONNECTIVITY_ATTR);
        if (!bluetoothConnectivity.isEmpty()) {
            currentDeviceType.setBluetoothConnectivity
                    (Boolean.parseBoolean(bluetoothConnectivity));
        }
    }

    private void buildDeviceCritical() {
        String criticalElemTextContent = getChildNodeTextContent
                (currentDeviceElem, "critical");
        boolean criticalElemContent = Boolean.parseBoolean(criticalElemTextContent);
        currentDevice.setCritical(criticalElemContent);
    }

    private static String getChildNodeTextContent
            (Element element, String elementName) {
        Node node = getChildNode(element, elementName);
        return node.getTextContent();
    }

    private static Node getChildNode(Element element, String elementName) {

        NodeList nodeList = element.getElementsByTagName(elementName);
        Node childNode = nodeList.item(0);

        if (childNode == null) {
            throw new XMLTagNotSupportedException();
        }

        return childNode;
    }

    private static String getNodeAttrTextContext(Element element, String attrName) {
        NamedNodeMap namedNodeMap = element.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node current = namedNodeMap.item(i);
            if (current.getNodeName().equals(attrName)) {
                return current.getTextContent();
            }
        }
        return "";
    }

}