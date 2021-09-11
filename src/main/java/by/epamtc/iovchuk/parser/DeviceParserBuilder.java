package by.epamtc.iovchuk.parser;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.parser.DOM.DeviceDOMParser;
import by.epamtc.iovchuk.parser.SAX.DeviceSAXParser;
import by.epamtc.iovchuk.parser.StAX.DeviceStAXParser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DeviceParserBuilder {

    private final String filepath;

    private CustomXMLParser<Device> deviceParser;

    public DeviceParserBuilder(String _filepath) {
        filepath = _filepath;
    }

    public DeviceParserBuilder sax() {
        deviceParser = new DeviceSAXParser(filepath);
        return this;
    }

    public DeviceParserBuilder stax() {
        deviceParser = new DeviceStAXParser(filepath);
        return this;
    }

    public DeviceParserBuilder dom() {
        deviceParser = new DeviceDOMParser(filepath);
        return this;
    }

    public Set<Device> parse() {
        Set<Device> devices = new HashSet<>();
        deviceParser.parse();
        Iterator<Device> devicesIterator = deviceParser.iterator();
        while (devicesIterator.hasNext()) {
            devices.add(devicesIterator.next());
        }
        return devices;
    }

}