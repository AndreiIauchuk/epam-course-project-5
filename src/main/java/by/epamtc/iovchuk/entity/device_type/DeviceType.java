package by.epamtc.iovchuk.entity.device_type;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceType)) return false;
        DeviceType that = (DeviceType) o;
        return peripheral == that.peripheral &&
                batteryCharged == that.batteryCharged &&
                coolerPresence == that.coolerPresence &&
                bluetoothConnectivity == that.bluetoothConnectivity &&
                Objects.equals(energyConsumption, that.energyConsumption) &&
                componentGroup == that.componentGroup &&
                Objects.equals(ports, that.ports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                peripheral, energyConsumption, batteryCharged, coolerPresence,
                componentGroup, ports, bluetoothConnectivity);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "peripheral=" + peripheral +
                ", energyConsumption='" + energyConsumption + '\'' +
                ", batteryCharged=" + batteryCharged +
                ", coolerPresence=" + coolerPresence +
                ", componentGroup=" + componentGroup +
                ", ports=" + ports +
                ", bluetoothConnectivity=" + bluetoothConnectivity +
                '}';
    }

}