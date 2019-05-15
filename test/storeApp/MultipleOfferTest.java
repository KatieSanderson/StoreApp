package storeApp;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MultipleOfferTest {

    private static final MultipleOffer MULTIPLE_OFFER_THREE_FOR_ONE = new MultipleOffer(3, new BigDecimal(1));
    private static final MultipleOffer MULTIPLE_OFFER_TWO_FOR_FIVE = new MultipleOffer(2, new BigDecimal(5));

    @Test
    public void totalSavingsTest_NoSet() {
        int quantity = 2;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertNull(MULTIPLE_OFFER_THREE_FOR_ONE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_OneSet() {
        int quantity = 3;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_DOWN), MULTIPLE_OFFER_THREE_FOR_ONE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_TwoSets() {
        int quantity = 6;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertEquals(new BigDecimal(4).setScale(2, RoundingMode.HALF_DOWN), MULTIPLE_OFFER_THREE_FOR_ONE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_NonIntPrice() {
        int quantity = 3;
        BigDecimal itemPrice = new BigDecimal(1.5);
        Assert.assertEquals(new BigDecimal(3.5).setScale(2, RoundingMode.HALF_DOWN), MULTIPLE_OFFER_THREE_FOR_ONE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_OneHalfSet() {
        int quantity = 4;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_DOWN), MULTIPLE_OFFER_THREE_FOR_ONE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_InvalidQuantity() {
        int quantity = 0;
        BigDecimal itemPrice = new BigDecimal(3);
        Assert.assertNull(MULTIPLE_OFFER_TWO_FOR_FIVE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_InvalidItemPrice() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(-1);
        Assert.assertNull(MULTIPLE_OFFER_TWO_FOR_FIVE.totalSavings(quantity, itemPrice));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest_InvalidCount() {
        new MultipleOffer(0, new BigDecimal(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest_InvalidTotalPrice() {
        new MultipleOffer(5, new BigDecimal(-1));
    }

    @Test
    public void totalSavingsTest_InvalidSavingsPerSets() {
        int quantity = 4;
        BigDecimal itemPrice = new BigDecimal(2);
        Assert.assertNull(MULTIPLE_OFFER_TWO_FOR_FIVE.totalSavings(quantity, itemPrice));
    }
}