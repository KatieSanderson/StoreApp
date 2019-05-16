package storeApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.fail;

public class StoreTest {

    private static final String PC1 = "H57";
    private static final String PC2 = "C330";
    private static final Sku SKU1 = new Sku(PC1, "Tin O Beans", new BigDecimal(1.23));
    private static final Sku SKU2 = new Sku(PC2, "Fruity drink", new BigDecimal(0.54));
    private Store store;

    @Before
    public void setup() {
        store = new Store();
    }

    @Test
    public void getSkuFromProductCodeTest_Successful() throws DuplicateSkuException {
        store.addSku(SKU1);
        store.addSku(SKU2);
        Assert.assertEquals(Optional.of(SKU1), store.getSkuFromProductCode(PC1));
    }

    @Test
    public void getSkuFromProductCodeTest_Unsuccessful() {
        Assert.assertEquals(Optional.empty(), store.getSkuFromProductCode("Missing Product Code"));
    }

    @Test
    public void addSkuTest_Successful() throws DuplicateSkuException {
        Assert.assertTrue(store.getSkus().isEmpty());
        store.addSku(SKU1);
        Assert.assertTrue(store.getSkus().containsValue(SKU1));
    }

    @Test
    public void addSkuTest_DuplicateAdded() {
        fail();
    }

}