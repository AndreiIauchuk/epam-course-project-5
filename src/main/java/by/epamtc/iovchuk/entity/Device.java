package by.epamtc.iovchuk.entity;

import by.epamtc.iovchuk.entity.device_info.Price;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Device implements Serializable {

    private static final long serialVersionUID = -7758822977616457916L;

    private int id;
    private String name;
    private String origin;
    private Price price;
    private DeviceType deviceType;
    private boolean critical;

    public Device() {
    }

    public Device(Price price, DeviceType type) {
        this.price = price;
        this.deviceType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return id == device.id &&
                critical == device.critical &&
                Objects.equals(name, device.name) &&
                Objects.equals(origin, device.origin) &&
                Objects.equals(price, device.price) &&
                Objects.equals(deviceType, device.deviceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, origin, price, deviceType, critical);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                ", price=" + price +
                ", deviceType=" + deviceType +
                ", critical=" + critical +
                '}';
    }

}