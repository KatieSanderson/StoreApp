package storeApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@link Store} includes a {@link Till} and {@link List} of {@link Sku}, which are added with {@link #addSku(Sku)} method.
 *
 * {@link #getSkuFromProductCode(String)} will:
 * - Given a {@link String} corresponding to a {@link Sku} in the skus {@link java.awt.List}, return an {@link Optional} with that {@link Sku}
 * - Given a {@link String} with no corresponding {@link Sku}, return an empty {@link Optional}
 *
 */

class Store {

    /**
     * skus is implemented as a List.
     *
     * Potentially other desired implementation: HashMap<productCode, Sku> to :
     *  - check for and prevent duplicated productCode Sku
     *  - allow constant-time lookup (for example, {@link #getSkuFromProductCode(String)})
     */
    private final Map<String, Sku> skus;
    private final Till till;

    Store() {
        skus = new HashMap<>();
        till = new Till(this);
    }

    Optional<Sku> getSkuFromProductCode(String productCode) {
        if (skus.containsKey(productCode)) {
            return Optional.of(skus.get(productCode));
        } else {
            System.out.println("No SKU in store for product code [" + productCode + "]");
            return Optional.empty();
        }
    }

    void addSku(Sku sku) {
        if (skus.containsKey(sku.getProductCode())) {
            throw new IllegalStateException("Attempted duplicate product code parse: [" + sku.getProductCode() + "]");
        } else {
            skus.put(sku.getProductCode(), sku);
        }
    }

    Map<String, Sku> getSkus() {
        return skus;
    }

    Till getTill() {
        return till;
    }

}
