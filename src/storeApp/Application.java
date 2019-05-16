package storeApp;

import java.io.*;
import java.util.Scanner;

/**
 * {@link Application} reads from file given correct input argument (file path). Undefined behavior may result from incorrectly formatted argument(s)
 *
 * File is read, parsed into a {@link Sku} in a {@link Store} given correct content
 * Input is read from command line to identify {@link Sku} and add to a {@link Receipt} with {@link Saving}s, if any
 */

public class Application implements AutoCloseable {

    private final Scanner scanner;
    private final BufferedReader bufferedReader;
    private final Store store;

    Application(BufferedReader bufferedReader) {
        scanner = new Scanner(System.in);
        this.bufferedReader = bufferedReader;
        store = new Store();
    }

    public static void main(String[] args) throws IOException {
        try (Application application = new Application(new BufferedReader(new FileReader(new File(args[0]))))) {
            application.parseReader();
            application.scanItems();
            System.out.println(application.store.getTill().getReceipt());
        }
    }

    private void scanItems() {
        System.out.println("Please input product codes to be scanned:");
        String exitString = "*";
        String line;
        while (!(line = scanner.nextLine()).equals(exitString)) {
            store.getTill().scan(line);
        }
    }

    void parseReader() throws IOException {
        // reads the first line of the file (headers, do not contain Sku info)
        bufferedReader.readLine();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Sku nextSku = Sku.parseSku(line);
            try {
                store.addSku(nextSku);
            } catch (DuplicateSkuException e) {
                parseDuplicateSkuException(e);
            }

        }
    }

    private void parseDuplicateSkuException(DuplicateSkuException e) {
        Sku existingSku = e.getExistingSku();
        Sku invadingSku = e.getInvadingSku();
        System.out.println("Error: cannot add product code [" + invadingSku.getProductCode() + "] due to duplicate entry. Options:");
        for (DuplicateSkuResponse option : DuplicateSkuResponse.values()) {
            System.out.println(option.code + " - " + option.output);
        }
        System.out.println("Invading SKU... Product Code: " + invadingSku.getProductCode() + " Description: " + invadingSku.getDescription() + " Price: " + invadingSku.getPrice());
        System.out.println("Existing SKU... Product Code: " + existingSku.getProductCode() + " Description: " + existingSku.getDescription() + " Price: " + existingSku.getPrice());
        System.out.print("Please input how to proceed: ");
        int userResponse = Integer.parseInt(scanner.nextLine());
        for (DuplicateSkuResponse option : DuplicateSkuResponse.values()) {
            if (option.code == userResponse) {
                option.executeResponse(e.getExistingSku(), e.getInvadingSku(), store);
            }
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
        bufferedReader.close();
    }

    public Store getStore() {
        return store;
    }
}
