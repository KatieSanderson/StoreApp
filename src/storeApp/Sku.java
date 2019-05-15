package storeApp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * {@link Sku} is constructed with productCode, description, price, and, if applicable, an offer.
 *
 * Skus are equal if and only if their productCodes are equal.
 */

public class Sku implements Serializable {

    private String productCode;
    private String description;
    private BigDecimal price;
    private Offer offer;

    public Sku() {}

    Sku(String productCode, String description, BigDecimal price) {
        this.productCode = productCode;
        this.description = description;
        this.price = price;
    }

    Sku(String productCode, String description, BigDecimal price, Offer offer) {
        this(productCode, description, price);
        this.offer = offer;
    }

    /**
     * parseSku returns a {@link Sku} from correctly formatted String.
     * inputLine should be String with tab-separated words for productCode, description, price, and optional offer
     *
     * Undefined behavior if called with incorrectly formatted inputLine.
     */

    static Sku parseSku(String inputLine) {
        try {
            String[] inputArray = inputLine.split("\\t");
            BigDecimal itemPrice = new BigDecimal(inputArray[2]);
            if (inputArray.length > 3) {
                    String offerString = inputArray[3];
                    Offer offer;
                    switch (offerString) {
                        case "Half Price" :
                            offer = new HalfPriceOffer();
                            break;
                        case "BOGOF" :
                            offer = new BogofOffer(itemPrice);
                            break;
                        default :
                            String[] offerArray = offerString.split(" ");
                            int count = Integer.parseInt(offerArray[0]);
                            BigDecimal totalPrice = new BigDecimal(offerArray[2]).setScale(2, RoundingMode.HALF_DOWN);
                            offer = new MultipleOffer(count, totalPrice);
                    }
                    return new Sku(inputArray[0], inputArray[1], itemPrice, offer);
            } else {
                return new Sku(inputArray[0], inputArray[1], itemPrice);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid offer [" + inputLine + "]");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productCode);
    }

    /**
     * Two {@link Sku}s are equal if and only if their productCodes are equal.
     *
     * (An alternative equals implementation is: equal if and only if productCodes, descriptions, and prices are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Sku.class) {
            return false;
        }
        Sku that = (Sku) obj;
        return Objects.equals(this.productCode, that.productCode);
    }

    @Override
    public String toString() {
        return description + " (" + productCode +
                ") price: " + price;
    }

    boolean hasOffer() {
        return getOffer() != null;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Offer getOffer() {
        return offer;
    }
}
