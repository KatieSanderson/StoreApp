package storeApp;

import java.math.BigDecimal;

/**
 * Items are buy one get one free (BOGOF).
 *
 * See {@link MultipleOffer} for documented behavior.
 */
public class BogofOffer extends MultipleOffer {

    BogofOffer(BigDecimal totalPrice) {
        super(2, totalPrice);
    }

    @Override
    public BigDecimal totalSavings(int quantity, BigDecimal itemPrice) {
        return super.totalSavings(quantity, itemPrice);
    }

    @Override
    public String toString() {
        return "Buy One Get One Free";
    }
}
