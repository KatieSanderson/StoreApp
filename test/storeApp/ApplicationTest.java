package storeApp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private static final String PRODUCT1 = "H57\tTin o Beans\t1.23";
    private static final String PRODUCT2 = "C330\tFruity drink\t0.54";
    private static final String PRODUCT3 = "BR7\tSliced loaf\t1.54";
    private static final Sku SKU1 = new Sku("H57", "Tin o Beans", new BigDecimal("1.23"));
    private static final Sku SKU2 = new Sku("C330", "Fruity drink", new BigDecimal("0.54"));
    private static final Sku SKU3 = new Sku("BR7", "Sliced loaf", new BigDecimal("1.54"));
    private static final List<Sku> SKUS = new ArrayList<>(Arrays.asList(SKU1, SKU2, SKU3));
    private Application application;

    @Mock
    private BufferedReader bufferedReader;

    @Before
    public void setup() {
        application = new Application(bufferedReader);
    }

    @Test
    public void parseFileTest_Successful() throws IOException {
        when(bufferedReader.readLine())
                .thenReturn("Headers").thenReturn(PRODUCT1).thenReturn(PRODUCT2).thenReturn(PRODUCT3).thenReturn(null);
        application.parseReader();
        Map<String, Sku> skus = application.getStore().getSkus();
        for (Map.Entry<String,Sku> mapEntry : skus.entrySet()) {
            Assert.assertTrue(SKUS.contains(mapEntry.getValue()));
        }
    }

}