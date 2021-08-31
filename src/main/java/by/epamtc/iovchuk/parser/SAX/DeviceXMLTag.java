package by.epamtc.iovchuk.parser.SAX;

public enum DeviceXMLTag {

    DEVICES("devices"),
    ID("id"),
    NAME("name"),
    ORIGIN("origin"),
    PRICE("price"),
    TYPE("type"),
    PERIPHERAL("peripheral"),
    ENERGY_CONSUMPTION("energy_consumption"),
    COOLER_PRESENCE("cooler_presence"),
    PORT("port"),
    CRITICAL("critical");

    private final String value;

    DeviceXMLTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}