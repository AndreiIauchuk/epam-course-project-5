package by.epamtc.iovchuk.resource_manager;

import by.epamtc.iovchuk.exception.ResourceManagerException;

import java.util.ResourceBundle;

public class DeviceResourceManager {

    private static final DeviceResourceManager instance = new DeviceResourceManager();

    private final ResourceBundle resourceBundle =
            ResourceBundle.getBundle("device");

    private DeviceResourceManager(){}

    public static DeviceResourceManager getInstance() {
        return instance;
    }

    public String getString(String key) {
        try {
            return (String) resourceBundle.getObject(key);
        } catch (ClassCastException e) {
            throw new ResourceManagerException
                    ("Key " + key + "must contains String value!");
        }
    }

}