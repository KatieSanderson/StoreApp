package storeApp;

public class DuplicateSkuException extends Exception {

    private final Sku existingSku;
    private final Sku invadingSku;
    private final String message;

    public DuplicateSkuException(Sku existingSku, Sku invadingSku, String message) {
        this.existingSku = existingSku;
        this.invadingSku = invadingSku;
        this.message = message;
    }

    public Sku getExistingSku() {
        return existingSku;
    }

    public Sku getInvadingSku() {
        return invadingSku;
    }
}
