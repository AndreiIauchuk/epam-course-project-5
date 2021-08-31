package by.epamtc.iovchuk.entity;

import by.epamtc.iovchuk.entity.device_info.Price;
import by.epamtc.iovchuk.entity.device_type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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

}