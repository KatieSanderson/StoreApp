package storeApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TillTest {

    private static final String PRODUCT_CODE = "C330";
    private static final Sku SKU1 = new Sku("H57", "Tin O Beans", new BigDecimal(1.23));
    private static final Sku SKU2 = new Sku("C330", "Fruity drink", new BigDecimal(0.54));
    private static final Sku SKU3 = new Sku("BR7", "Sliced loaf", new BigDecimal(1.54));
    private static final Sku SKU_OFFER = new Sku("BR7", "Sliced loaf", new BigDecimal(1.54), new HalfPriceOffer());
    private static final Optional<Sku> OPTIONAL_SKU_1 = Optional.of(SKU1);
    private static final Optional<Sku> OPTIONAL_SKU_2 = Optional.of(SKU2);
    private static final Optional<Sku> optionalEmptySku = Optional.empty();
    private static final List<Sku> skus = new ArrayList<>(Arrays.asList(SKU1, SKU2, SKU3));

    @Mock
    private Store store;

    private Till till;

    @Before
    public void setup() {
        till = new Till(store);
    }

    @Test
    public void scanTest_SuccessfulAdd() {
        when(store.getSkuFromProductCode(any(String.class))).thenReturn(OPTIONAL_SKU_2);
        till.scan(PRODUCT_CODE);
        Assert.assertTrue(till.getScannedItems().contains(OPTIONAL_SKU_2.get()));
    }

    @Test
    public void scanTest_NotAdded() {
        when(store.getSkuFromProductCode(any(String.class))).thenReturn(optionalEmptySku);
        till.scan(PRODUCT_CODE);
        Assert.assertTrue(till.getScannedItems().isEmpty());
    }

    @Test
    public void getReceiptTest_NoOffers() {
        Optional<Sku> optionalSku3 = Optional.of(SKU3);
        when(store.getSkuFromProductCode(any(String.class))).thenReturn(OPTIONAL_SKU_1).thenReturn(OPTIONAL_SKU_2).thenReturn(optionalSku3);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        Receipt receipt = till.getReceipt();
        Receipt testReceipt = new Receipt(skus, new ArrayList<>());
        Assert.assertEquals(testReceipt, receipt);
    }

    @Test
    public void getReceiptTest_OneOffer() {
        Sku skuOffer = new Sku("BR7", "Sliced loaf", new BigDecimal(1.54), new HalfPriceOffer());
        Offer offer = skuOffer.getOffer();
        List<Saving> savings = new ArrayList<>(Arrays.asList(new Saving(skuOffer.getDescription(), offer, offer.totalSavings(1, skuOffer.getPrice()))));
        Optional<Sku> optionalSkuOffer = Optional.of(skuOffer);
        when(store.getSkuFromProductCode(any(String.class))).thenReturn(OPTIONAL_SKU_1).thenReturn(OPTIONAL_SKU_2).thenReturn(optionalSkuOffer);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        Receipt receipt = till.getReceipt();
        Receipt testReceipt = new Receipt(skus, savings);
        Assert.assertEquals(testReceipt, receipt);
    }

    @Test
    public void getReceiptTest_OneNotAdded() {
        when(store.getSkuFromProductCode(any(String.class))).thenReturn(OPTIONAL_SKU_1).thenReturn(OPTIONAL_SKU_2).thenReturn(optionalEmptySku);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        Assert.assertEquals(2, till.getScannedItems().size());
        for (int i = 0; i < till.getScannedItems().size(); i++) {
            Assert.assertEquals(skus.get(i), till.getScannedItems().get(i));
        }
    }

    @Test
    public void getReceiptTest_NoneAdded() {
        when(store.getSkuFromProductCode(any(String.class))).thenReturn(optionalEmptySku).thenReturn(optionalEmptySku).thenReturn(optionalEmptySku);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        till.scan(PRODUCT_CODE);
        Assert.assertTrue(till.getScannedItems().isEmpty());
    }
}