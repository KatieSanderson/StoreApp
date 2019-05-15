package storeApp;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Offer extends Serializable {

    BigDecimal totalSavings(int quantity, BigDecimal itemPrice);

}
