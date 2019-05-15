package storeApp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * {@link Receipt} is constructed from the final (no further additions, deletions; transaction are complete) version of:
 * - a {@link List} of {@link Sku} representing the scanned {@link Sku} from {@link Till}
 * - a {@link List} of {@link Saving} representing the savings from the scanned {@link Sku}
 *
 *
 * {@link #toString()} is a user-friendly view of {@link Receipt} and its fields.
 */

class Receipt {

    private final List<Sku> receipt;
    private final List<Saving> savings;
    private final BigDecimal totalSavings;
    private final BigDecimal costBeforeSavings;

    Receipt(List<Sku> receipt, List<Saving> savings) {
        this.receipt = receipt;
        this.savings = savings;
        Function<Saving, BigDecimal> getTotalSaving = Saving::getTotalSaving;
        totalSavings = calculateTotal(savings, getTotalSaving);
        Function<Sku, BigDecimal> getPrice = Sku::getPrice;
        costBeforeSavings = calculateTotal(receipt, getPrice);
    }

    private <T> BigDecimal calculateTotal(List<T> objects, Function<T, BigDecimal> function) {
        BigDecimal temp = new BigDecimal(0);
        for (T object : objects) {
            temp = temp.add(function.apply(object));
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Receipt: \n");
        for (Sku sku: receipt) {
            stringBuilder.append(sku.toString()).append("\n");
        }
        if (savings.size() > 0) {
            stringBuilder.append("Cost before savings: ").append(costBeforeSavings).append("\n\n");
            for (Saving saving : savings) {
                stringBuilder.append(saving).append("\n");
            }
            stringBuilder.append("Total savings: ").append(totalSavings).append("!\n\n");
        }
        stringBuilder.append("Final cost: ").append(costBeforeSavings.subtract(totalSavings)).append("\n");
        stringBuilder.append("Please come again!");
        return stringBuilder.toString();
    }

    public BigDecimal getTotalSavings() {
        return totalSavings;
    }

    public BigDecimal getCostBeforeSavings() {
        return costBeforeSavings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt that = (Receipt) o;
        return Objects.equals(this.receipt, that.receipt) &&
                Objects.equals(this.savings, that.savings) &&
                Objects.equals(this.totalSavings, that.totalSavings) &&
                Objects.equals(this.costBeforeSavings, that.costBeforeSavings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receipt, savings, totalSavings, costBeforeSavings);
    }
}
