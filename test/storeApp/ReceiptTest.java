package storeApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptTest {

    @Mock private static Sku sku;

    @Mock private static Saving saving;

    private List<Sku> skus;
    private List<Saving> savings;

    private Receipt receipt;

    @Before
    public void setup() {
        when(sku.getPrice())
                .thenReturn(new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN))
                .thenReturn(new BigDecimal(2.5).setScale(2, RoundingMode.HALF_DOWN))
                .thenReturn(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_DOWN));
        when(saving.getTotalSaving())
                .thenReturn(new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN))
                .thenReturn(new BigDecimal(2.5).setScale(2, RoundingMode.HALF_DOWN))
                .thenReturn(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_DOWN));
        savings = new ArrayList<>(Arrays.asList(saving, saving, saving));
        skus = new ArrayList<>(Arrays.asList(sku, sku, sku));
        receipt = new Receipt(skus, savings);
    }

    @Test
    public void getTotalSavingsTest() {
        Assert.assertEquals(new BigDecimal(5).setScale(2, RoundingMode.HALF_DOWN), receipt.getTotalSavings());
    }

    @Test
    public void getCostBeforeSavings() {
        Assert.assertEquals(new BigDecimal(5).setScale(2, RoundingMode.HALF_DOWN), receipt.getCostBeforeSavings());
    }

}