package by.epamtc.iovchuk.entity.device_info;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Price implements Serializable {

    private static final long serialVersionUID = 2883660323440369385L;

    private Currency currency;
    private double value;

}