package storeApp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link Till} is constructed with empty lists. {
 *
 * @link Till #scan} adds a {@link Sku} to the scannedItems, if a corresponding {@link Sku} is found via @{@link Store #getSkuFromProductCode}.
 *
 * {@link Till #getReceipt} determines offers and savings after all {@link String} are parsed into {@link Sku}.
 * If applicable, a {@link Sku} with an {@link Offer} is added to offers {@link Map}.
 * New {@link Saving}s are instantiated if valid and totalSavings is updated.
 * A {@link Receipt} with all scannedItems and {@link Saving}s are returned.
 */

public class Till {

    private final List<Sku> scannedItems;
    private final List<Saving> savings;
    private final Map<Sku, Integer> offers;
    private final Store store;
    private BigDecimal totalSavings;

    Till(Store store) {
        scannedItems = new ArrayList<>();
        savings = new ArrayList<>();
        offers = new HashMap<>();
        this.store = store;
        totalSavings = new BigDecimal(0);
    }

    void scan(String productCode) {
        store.getSkuFromProductCode(productCode).ifPresent(scannedItems::add);
    }

    /**
     * If a {@link Sku} has an offer, adds to offers.
     * On each valid saving:
     *  - Adds any valid savings to totalSavings
     *  - Creates new {@link Saving} and adds to savings
     * @return
     */
    Receipt getReceipt() {
        checkScannedItemsForOffer();
        addSavings();
        return new Receipt(scannedItems, savings);
    }

    private void addSavings() {
        for (Map.Entry<Sku, Integer> mapEntry : offers.entrySet()) {
            Sku sku = mapEntry.getKey();
            int quantity = mapEntry.getValue();
            Offer offer = sku.getOffer();
            // calculates savings for offer (applied to all corresponding SKUs)
            BigDecimal totalSaving = offer.totalSavings(quantity, sku.getPrice());
            if (totalSaving != null) {
                totalSavings = totalSavings.add(totalSaving);
                savings.add(new Saving(sku.getDescription(), offer, totalSaving));
            }
        }
    }

    private void checkScannedItemsForOffer() {
        for (Sku sku : scannedItems) {
            if (sku.hasOffer()) {
                addOffer(sku);
            }
        }
    }

    private void addOffer(Sku sku) {
        if(!offers.containsKey(sku)) {
            offers.put(sku, 0);
        }
        offers.put(sku, offers.get(sku) + 1);
    }

    List<Sku> getScannedItems() {
        return scannedItems;
    }
}
