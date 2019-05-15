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

    Application(BufferedReader bufferedReader) throws FileNotFoundException {
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
            store.addSku(nextSku);
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