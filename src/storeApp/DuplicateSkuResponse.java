package storeApp;

public enum DuplicateSkuResponse {
    OVERWRITE(1, "Overwrite existing product info"),
    KEEP(2, "Keep existing product info");

    public int code;
    public String output;

    DuplicateSkuResponse(int code, String output) {
        this.code = code;
        this.output = output;
    }

}
