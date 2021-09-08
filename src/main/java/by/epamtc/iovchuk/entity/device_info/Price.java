package by.epamtc.iovchuk.entity.device_info;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Price implements Serializable {

    private static final long serialVersionUID = 2883660323440369385L;

    private Currency currency;
    private Double value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return currency == price.currency &&
                Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "currency=" + currency +
                ", value=" + value +
                '}';
    }

}