package storeApp;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class SavingTest {

    private static final Saving SAVING1 = new Saving("", new HalfPriceOffer(), new BigDecimal(1));
    private static final Saving SAVING1b = new Saving("", new HalfPriceOffer(), new BigDecimal(1));
    private static final Saving SAVING2 = new Saving("abc", new BogofOffer(new BigDecimal(1)), new BigDecimal(2));
    private static final Object OBJECT = new Object();
    private static final Saving SAVING_NULL = new Saving(null, null, null);

    @Test
    public void hashCodeTest_EqualObjects() {
        Assert.assertEquals(SAVING1.hashCode(), SAVING1b.hashCode());
    }

    @Test
    public void hashCodeTest_SameClass() {
        Assert.assertNotEquals(SAVING2.hashCode(), SAVING1.hashCode());
    }

    @Test
    public void hashCodeTest_DifferentClasses() {
        Assert.assertNotEquals(OBJECT.hashCode(), SAVING1.hashCode());
    }

    @Test
    public void hashCodeTest_Null() {
        Assert.assertNotEquals(SAVING1, SAVING_NULL);
    }

    @Test
    public void equalsTest_EqualObjects() {
        Assert.assertEquals(SAVING1, SAVING1b);
    }

    @Test
    public void equalsTest_SameClass() {
        Assert.assertNotEquals(SAVING1, SAVING2);
    }

    @Test
    public void equalsTest_DifferentClasses() {
        Assert.assertNotEquals(SAVING1, OBJECT);
    }

    @Test
    public void equalsTest_Null() {
        Assert.assertNotEquals(SAVING1, SAVING_NULL);
    }
}