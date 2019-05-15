package storeApp;

import java.util.ArrayList;
import java.util.List;
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
    private final List<Sku> skus;
    private final Till till;

    Store() {
        skus = new ArrayList<>();
        till = new Till(this);
    }

    Optional<Sku> getSkuFromProductCode(String productCode) {
        for (Sku sku : skus) {
            if (sku.getProductCode().equals(productCode)) {
                return Optional.of(sku);
            }
        }
        System.out.println("No SKU in store for product code [" + productCode + "]");
        return Optional.empty();
    }

    void addSku(Sku sku) {
        skus.add(sku);
    }

    List<Sku> getSkus() {
        return skus;
    }

    Till getTill() {
        return till;
    }

}
