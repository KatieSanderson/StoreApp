package storeApp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * {@link Saving} description refers to description of SKU instantiating Saving
 * {@link Saving} totalSaving refers to total savings from this instance of Saving
 */

public class Saving implements Serializable {

    private String description;
    private Offer savingType;
    private BigDecimal totalSaving;

    public Saving() {}

    Saving(String description, Offer savingType, BigDecimal totalSaving) {
        this.description = description;
        this.savingType = savingType;
        this.totalSaving = totalSaving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Saving that = (Saving) o;
        return Objects.equals(this.description, that.description) &&
                Objects.equals(this.savingType, that.savingType) &&
                Objects.equals(this.totalSaving, that.totalSaving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, savingType, totalSaving);
    }

    @Override
    public String toString() {
        return  description +
                ": " + totalSaving +
                " saved with offer " + savingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Offer getSavingType() {
        return savingType;
    }

    public void setSavingType(Offer savingType) {
        this.savingType = savingType;
    }

    public BigDecimal getTotalSaving() {
        return totalSaving;
    }

    public void setTotalSaving(BigDecimal totalSaving) {
        this.totalSaving = totalSaving;
    }
}
