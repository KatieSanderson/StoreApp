package storeApp;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BogofOfferTest {

    private static final BogofOffer BOGOF_OFFER_ONE = new BogofOffer(new BigDecimal(1));
    private static final BogofOffer BOGOF_OFFER_FIVE = new BogofOffer(new BigDecimal(5));
    private static final BogofOffer BOGOF_OFFER_FIVEFIFTY = new BogofOffer(new BigDecimal(5.5));

    @Test
    public void totalSavingsTest_NoSet() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(5);
        Assert.assertNull(BOGOF_OFFER_FIVEFIFTY.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_OneSet() {
        int quantity = 2;
        BigDecimal itemPrice = new BigDecimal(1);
        Assert.assertEquals(new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN), BOGOF_OFFER_ONE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_MultipleSets() {
        int quantity = 4;
        BigDecimal itemPrice = new BigDecimal(5);
        Assert.assertEquals(new BigDecimal(10).setScale(2, RoundingMode.HALF_DOWN), BOGOF_OFFER_FIVE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_OneHalfSet() {
        int quantity = 3;
        BigDecimal itemPrice = new BigDecimal(5);
        Assert.assertEquals(new BigDecimal(5).setScale(2, RoundingMode.HALF_DOWN), BOGOF_OFFER_FIVE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_NonIntPrice() {
        int quantity = 2;
        BigDecimal itemPrice = new BigDecimal(5.5);
        Assert.assertEquals(new BigDecimal(5.5).setScale(2, RoundingMode.HALF_DOWN), BOGOF_OFFER_FIVEFIFTY.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_InvalidQuantity() {
        int quantity = 0;
        BigDecimal itemPrice = new BigDecimal(3);
        Assert.assertNull(BOGOF_OFFER_FIVE.totalSavings(quantity, itemPrice));
    }

    @Test
    public void totalSavingsTest_InvalidItemPrice() {
        int quantity = 1;
        BigDecimal itemPrice = new BigDecimal(-1);
        Assert.assertNull(BOGOF_OFFER_FIVE.totalSavings(quantity, itemPrice));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest_InvalidTotalPrice() {
        new BogofOffer(new BigDecimal(-1));
    }

}