package by.epamtc.iovchuk.resource_manager;

import by.epamtc.iovchuk.exception.ResourceManagerException;

import java.util.ResourceBundle;

public class DeviceResourceManager {

    private static class DeviceResourceManagerHolder {
        static final DeviceResourceManager INSTANCE = new DeviceResourceManager();
    }

    public static DeviceResourceManager getInstance() {
        return DeviceResourceManagerHolder.INSTANCE;
    }

    private DeviceResourceManager(){}

    public String getString(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("device");
        try {
            return (String) resourceBundle.getObject(key);
        } catch (ClassCastException e) {
            throw new ResourceManagerException
                    ("Key " + key + "must contains String value!");
        }
    }

}