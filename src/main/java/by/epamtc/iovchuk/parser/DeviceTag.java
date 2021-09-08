package by.epamtc.iovchuk.parser;

public enum DeviceTag {

    DEVICES("devices"),
    DEVICE("device"),
    TYPE("type"),
    ID("id"),
    NAME("name"),
    ORIGIN("origin"),
    PRICE("price"),
    CURRENCY_ATTR("currency"),
    PERIPHERAL("peripheral"),
    ENERGY_CONSUMPTION("energy_consumption"),
    BATTERY_CHARGED("battery-charged"),
    COOLER_PRESENCE("cooler_presence"),
    COMPONENT_GROUP("component_group"),
    PORT("port"),
    BLUETOOTH_CONNECTIVITY("bluetooth-connectivity"),
    CRITICAL("critical");

    private final String value;

    DeviceTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}