package by.epamtc.iovchuk.parser.DOM;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.entity.device_info.Currency;
import by.epamtc.iovchuk.entity.device_info.Price;
import by.epamtc.iovchuk.entity.device_type.ComponentGroup;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import by.epamtc.iovchuk.entity.device_type.Port;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static by.epamtc.iovchuk.parser.DeviceTag.*;

public class DeviceDomBuilder {

   /* private static final String ELEMENT_DEVICE = "device";
    private static final String ELEMENT_ID = "id";
    private static final String ELEMENT_NAME = "name";
    private static final String ELEMENT_ORIGIN = "origin";
    private static final String ELEMENT_PORT = "port";

    private static final String ELEMENT_TYPE = "type";
    private static final String ELEMENT_PERIPHERAL = "peripheral";
    private static final String ELEMENT_ENERGY_CONSUMPTION = "energy_consumption";
    private static final String ELEMENT_COOLER_PRESENCE = "cooler_presence";
    private static final String ELEMENT_CRITICAL = "critical";*/

    private final Set<Device> devices;

    private DocumentBuilder docBuilder;

    private Device currentDevice;
    private Element currentDeviceElem;
    private Price currentPrice;
    private Element currentPriceElem;
    private DeviceType currentDeviceType;
    private Element currentDeviceTypeElem;

    public DeviceDomBuilder() {
        devices = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace(); // log
        }
    }

    /**
     *
     */
    public Iterator<Device> getDevicesIterator() {
        return devices.iterator();
    }

    /**
     *
     */
    public void buildDevices(String filename) {
        Document document;
        try {
            document = docBuilder.parse(filename);
            Element root = document.getDocumentElement();
            NodeList devicesElem = root.getElementsByTagName(DEVICE.getValue());

            for (int i = 0; i < devicesElem.getLength(); i++) {
                currentDeviceElem = (Element) devicesElem.item(i);
                buildDevice();
                devices.add(currentDevice);
            }
        } catch (IOException | SAXException e) {
            e.printStackTrace(); // log
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
        String idElemTextContent = getChildNodeTextContent
                (currentDeviceElem, ID.getValue());
        int id = Integer.parseInt(idElemTextContent.substring(3));
        currentDevice.setId(id);
    }

    private void buildDeviceName() {
        String nameElemTextContent = getChildNodeTextContent
                (currentDeviceElem, NAME.getValue());
        currentDevice.setName(nameElemTextContent);
    }

    private void buildDeviceOrigin() {
        String originElemTextContent = getChildNodeTextContent
                (currentDeviceElem, ORIGIN.getValue());
        currentDevice.setOrigin(originElemTextContent);
    }

    private void buildDevicePrice() {
        currentPrice = new Price();
        currentPriceElem = (Element) getChildNode
                (currentDeviceElem, PRICE.getValue());

        buildPriceCurrency();
        buildPriceValue();

        currentDevice.setPrice(currentPrice);
    }

    private void buildPriceCurrency() {
        String currentAttrTextContent = getNodeAttrTextContext
                (currentPriceElem, CURRENCY_ATTR.getValue());
        Currency currency = Currency.valueOf(currentAttrTextContent);
        currentPrice.setCurrency(currency);
    }

    private void buildPriceValue() {
        Double priceValue = Double.parseDouble(currentPriceElem.getTextContent());
        currentPrice.setValue(priceValue);
    }

    private void buildDeviceType() {
        currentDeviceType = new DeviceType();
        currentDeviceTypeElem = (Element) getChildNode
                (currentDeviceElem, TYPE.getValue());

        buildDeviceTypePeripheral();
        buildDeviceTypeEnergyConsumption();
        buildDeviceTypeCoolerPresence();
        buildDeviceTypeComponentGroup();
        buildDevicePort();

        currentDevice.setDeviceType(currentDeviceType);
    }

    private void buildDeviceTypePeripheral() {
        String peripheralTextContent = getChildNodeTextContent
                (currentDeviceTypeElem, PERIPHERAL.getValue());
        boolean peripheral = Boolean.parseBoolean(peripheralTextContent);
        currentDeviceType.setPeripheral(peripheral);
    }

    private void buildDeviceTypeEnergyConsumption() {
        Element energyConsumptionElem = (Element) getChildNode
                (currentDeviceTypeElem, ENERGY_CONSUMPTION.getValue());
        String batteryChargedTextContent = getNodeAttrTextContext
                (energyConsumptionElem, BATTERY_CHARGED.getValue());
        boolean batteryCharged = Boolean.parseBoolean(batteryChargedTextContent);
        String energyConsumptionTextContent = energyConsumptionElem.getTextContent();
        currentDeviceType.setBatteryCharged(batteryCharged);
        currentDeviceType.setEnergyConsumption(energyConsumptionTextContent);
    }

    private void buildDeviceTypeCoolerPresence() {
        String coolerPresenceTextContent = getChildNodeTextContent
                (currentDeviceTypeElem, COOLER_PRESENCE.getValue());
        boolean coolerPresence = Boolean.parseBoolean(coolerPresenceTextContent);
        currentDeviceType.setCoolerPresence(coolerPresence);
    }

    private void buildDeviceTypeComponentGroup() {
        String componentGroupTextContent = getChildNodeTextContent
                (currentDeviceTypeElem, COMPONENT_GROUP.getValue());
        ComponentGroup componentGroup = ComponentGroup.fromString(componentGroupTextContent);
        currentDeviceType.setComponentGroup(componentGroup);
    }

    private void buildDevicePort() {
        Element portElem = (Element) getChildNode
                (currentDeviceTypeElem, PORT.getValue());
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
                (portElem, BLUETOOTH_CONNECTIVITY.getValue());
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
        return nodeList.item(0);
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