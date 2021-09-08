package by.epamtc.iovchuk.entity.device_type;

public enum ComponentGroup {
    INPUT_OUTPUT("input-output"),
    MULTIMEDIA("multimedia");

    private final String value;

    ComponentGroup(String value) {
        this.value = value;
    }

    static public ComponentGroup fromString(String value) {
        for (ComponentGroup group : ComponentGroup.values()) {
            if (group.value.equals(value)) {
                return group;
            }
        }
        return null;
    }
}