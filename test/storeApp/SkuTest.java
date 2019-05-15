package storeApp;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SkuTest {

    private static final Sku SKU1 = new Sku("H57", "Tin o Beans", new BigDecimal("1.23"));
    private static final Sku SKU1B = new Sku("H57", "Tin o Beans", new BigDecimal("1.23"));
    private static final Sku SKU2 = new Sku("C330", "Fruity drink", new BigDecimal("0.54"));
    private static final Sku SKU_NULL = new Sku(null, null, null);
    private static final Object OBJECT = new Object();
    private static final String STR1 = "H57\tTin o Beans\t1.23";

    @Test
    public void hashCodeTest_EqualObjects() {
        Assert.assertEquals(SKU1.hashCode(), SKU1B.hashCode());
    }

    @Test
    public void hashCodeTest_SameClass() {
        Assert.assertNotEquals(SKU1.hashCode(), SKU2.hashCode());
    }

    @Test
    public void hashCodeTest_DifferentClasses() {
        Assert.assertNotEquals(OBJECT.hashCode(), SKU2.hashCode());
    }

    @Test
    public void hashCodeTest_Null() {
        Assert.assertNotEquals(SKU1, SKU_NULL);
    }

    @Test
    public void equalsTest_EqualObjects() {
        Assert.assertEquals(SKU1, SKU1B);
    }

    @Test
    public void equalsTest_SameClass() {
        Assert.assertNotEquals(SKU1, SKU2);
    }

    @Test
    public void equalsTest_DifferentClasses() {
        Assert.assertNotEquals(SKU1, OBJECT);
    }

    @Test
    public void equalsTest_Null() {
        Assert.assertNotEquals(SKU1, SKU_NULL);
    }

    @Test
    public void parseSkuTest_NoOffer() {
        Sku parsedSku = Sku.parseSku(STR1);
        Assert.assertEquals(SKU1.getProductCode(), parsedSku.getProductCode());
        Assert.assertEquals(SKU1.getDescription(), parsedSku.getDescription());
        Assert.assertEquals(SKU1.getPrice(), parsedSku.getPrice());
    }

    @Test
    public void parseSkuTest_BOGOFOffer() {
        String strBOGOF = "C330\tFruity drink\t0.54\tBOGOF";
        Offer offerBOGOF = new BogofOffer(new BigDecimal(0.54).setScale(2, RoundingMode.HALF_DOWN));
        Sku parsedSku = Sku.parseSku(strBOGOF);
        Assert.assertEquals(offerBOGOF, parsedSku.getOffer());
    }

    @Test
    public void parseSkuTest_MultipleOffer() {
        String strMultiple = "C330\tFruity drink\t0.54\t3 for 1.00";
        Offer offerMultiple = new MultipleOffer(3, new BigDecimal(1.00).setScale(2, RoundingMode.HALF_DOWN));
        Sku parsedSku = Sku.parseSku(strMultiple);
        Assert.assertEquals(offerMultiple, parsedSku.getOffer());
    }

    @Test
    public void parseSkuTest_HalfOffer() {
        String strHalf = "C330\tFruity drink\t0.54\tHalf Price";
        Offer offerHalf = new HalfPriceOffer();
        Sku parsedSku = Sku.parseSku(strHalf);
        Assert.assertEquals(offerHalf, parsedSku.getOffer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseSkuTest_MultipleOfferException() {
        String strMultipleException = "C330\tFruity drink\t0.54\tBAD PARSE";
        Sku.parseSku(strMultipleException);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseSkuTest_OfferException() {
        String strBadOffer = "Bad Offer";
        Sku.parseSku(strBadOffer);
    }

    @Test
    public void hasOfferTestTrue() {
        Sku sku = new Sku("C330", "Fruity drink", new BigDecimal("0.54"), new HalfPriceOffer());
        Assert.assertTrue(sku.hasOffer());
    }

    @Test
    public void hasOfferTestFalse() {
        Assert.assertFalse(SKU2.hasOffer());
    }
}