package storeApp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Items are multiple offer of quantity for a set price, for example 3 for 1.00.
 *
 * Items of same offer will be same item/productCode, therefore same price.
 * There are no additional savings for incomplete offer sets.
 * Null savings are returned for invalid input.
 * Offers are equal if their count and totalPrice are equal.
 */
public class MultipleOffer implements Offer {

    private final int count;
    private final BigDecimal totalPrice;
    private static final int MIN_COUNT = 1;
    private static final int MIN_TOTAL_PRICE = 0;
    private static final int MIN_SAVINGS_PER_SET = 0;

    MultipleOffer(int count, BigDecimal totalPrice) {
        if (count < MIN_COUNT) {
            throw new IllegalArgumentException("Invalid count [" + count + "]. Minimum allowable amount: " + MIN_COUNT);
        } else if (totalPrice.intValue() < MIN_TOTAL_PRICE) {
            throw new IllegalArgumentException("Invalid totalPrice [" + totalPrice.intValue() + "]. Minimum allowable amount: " + MIN_TOTAL_PRICE);
        }
        this.count = count;
        this.totalPrice = totalPrice;
    }

    @Override
    public BigDecimal totalSavings(int quantity, BigDecimal itemPrice) {
        if (quantity < count) {
            return null;
        } else if (itemPrice.intValue() < 0) {
            return null;
        }
        BigDecimal savingsPerSet = new BigDecimal(count).multiply(itemPrice).subtract(totalPrice);
        if (savingsPerSet.intValue() < MIN_SAVINGS_PER_SET) {
            return null;
        }
        int sets = quantity / count;
        return new BigDecimal(sets).multiply(savingsPerSet).setScale(2, RoundingMode.HALF_DOWN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, totalPrice);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MultipleOffer that = (MultipleOffer) obj;
        return this.count == that.count && Objects.equals(this.totalPrice, that.totalPrice);
    }

    @Override
    public String toString() {
        return count + " for " + totalPrice;
    }
}
