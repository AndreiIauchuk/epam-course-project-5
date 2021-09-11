package by.epamtc.iovchuk.parser;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.exception.XMLParserBuilderException;
import by.epamtc.iovchuk.exception.XMLParserException;
import by.epamtc.iovchuk.parser.DOM.DeviceDOMParser;
import by.epamtc.iovchuk.parser.SAX.DeviceSAXParser;
import by.epamtc.iovchuk.parser.StAX.DeviceStAXParser;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class DeviceParserBuilder {

    private final String filepath;

    private CustomXMLParser<Device> deviceParser;

    public DeviceParserBuilder(String _filepath) {
        filepath = _filepath;
    }

    public DeviceParserBuilder sax() throws XMLParserBuilderException {
        try {
            deviceParser = new DeviceSAXParser(filepath);
        } catch (XMLParserException e) {
            throw new XMLParserBuilderException(e.getMessage(), e);
        }
        return this;
    }

    public DeviceParserBuilder stax() throws XMLParserBuilderException {
        try {
            deviceParser = new DeviceStAXParser(filepath);
        } catch (XMLParserException e) {
            throw new XMLParserBuilderException(e.getMessage(), e);
        }
        return this;
    }

    public DeviceParserBuilder dom() throws XMLParserBuilderException {
        try {
            deviceParser = new DeviceDOMParser(filepath);
        } catch (XMLParserBuilderException e) {
            throw new XMLParserBuilderException(e.getMessage(), e);
        }
        return this;
    }

    public Set<Device> parse() throws XMLParserBuilderException {
        if (deviceParser == null) {
            throw new XMLParserBuilderException("Add a parser before parsing!");
        }

        try {
            deviceParser.parse();
        } catch (XMLParserException e) {
            throw new XMLParserBuilderException(e.getMessage(), e);
        }

        Set<Device> devices = new TreeSet<>();
        Iterator<Device> devicesIterator = deviceParser.iterator();
        while (devicesIterator.hasNext()) {
            devices.add(devicesIterator.next());
        }
        deviceParser = null;

        return devices;
    }

}