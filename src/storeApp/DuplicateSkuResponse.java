package storeApp;

public enum DuplicateSkuResponse {
    OVERWRITE(1, "Keep invading product info") {
        @Override
        public void executeResponse(Sku existingSku, Sku invadingSku, Store store) {
            store.getSkus().put(invadingSku.getProductCode(), invadingSku);
        }
    },
    KEEP(2, "Keep existing product info") {
        @Override
        public void executeResponse(Sku existingSku, Sku invadingSku, Store store) {
            store.getSkus().put(existingSku.getProductCode(), existingSku);
        }
    };

    public int code;
    public String output;

    DuplicateSkuResponse(int code, String output) {
        this.code = code;
        this.output = output;
    }

    public abstract void executeResponse(Sku existingSku, Sku invadingSku, Store store);

}
