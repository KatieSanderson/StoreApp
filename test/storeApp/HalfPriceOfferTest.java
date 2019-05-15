package storeApp;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HalfPriceOfferTest {

    private static final HalfPriceOffer HALF_PRICE_OFFER = new HalfPriceOffer();

    @Test
    public void totalSavingsTest_OneSet() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(2);
        Assert.assertEquals(new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN), HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_TwoSets() {
        int quantity = 2;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertEquals(new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN), HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_OddPrice() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(1.99);
        Assert.assertEquals(new BigDecimal(0.99).setScale(2, RoundingMode.HALF_DOWN), HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_TwoSetsOddPrice() {
        int quantity = 2;
        BigDecimal itemPrice = new BigDecimal(1.99);
        Assert.assertEquals(new BigDecimal(1.98).setScale(2, RoundingMode.HALF_DOWN), HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_IntToDecimalPrice() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertEquals(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_DOWN), HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_InvalidQuantity() {
        int quantity = 0;
        BigDecimal itemPrice = new BigDecimal(3);
        Assert.assertNull(HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_InvalidItemPrice() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(-1);
        Assert.assertNull(HALF_PRICE_OFFER.totalSavings(quantity, itemPrice));
    }

}