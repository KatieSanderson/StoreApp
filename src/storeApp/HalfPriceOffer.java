package storeApp;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Items are half price. If half price is not an integer number of pennies, we follow the {@link RoundingMode#HALF_DOWN} convention.
 * If multiple items are bought together, the half price offer applies to each individually.
 * For example, half price on a 1.99 item will be 1.00. Half price on two 1.99 items will be 2.00, not 1.99.
 */
public class HalfPriceOffer implements Offer {

    HalfPriceOffer() {
    }

    @Override
    public BigDecimal totalSavings(int quantity, BigDecimal itemPrice) {
        if (quantity < 1) {
            return null;
        } else if (itemPrice.intValue() < 0) {
            return null;
        }
        BigDecimal savingsPerSet = itemPrice.divide(new BigDecimal(2), 2, RoundingMode.HALF_DOWN);
        return new BigDecimal(quantity).multiply(savingsPerSet).setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && obj.getClass() == getClass();
    }

    @Override
    public String toString() {
        return "Half Price";
    }
}
