package by.epamtc.iovchuk.entity.device_type;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class DeviceType implements Serializable {

    private static final long serialVersionUID = 7301456992651858087L;

    private boolean peripheral;
    private String energyConsumption;
    private boolean batteryCharged;
    private boolean coolerPresence;
    private ComponentGroup componentGroup;
    private Set<Port> ports;
    private boolean bluetoothConnectivity;

}