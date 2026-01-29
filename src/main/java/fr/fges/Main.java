package fr.fges;

public class Main {

    public static void main(String[] args) {

        validateArguments(args);
        String storageFile = args[0];

        // Choose storage strategy
        StorageStrategy storage =
                storageFile.endsWith(".json")
                        ? new JsonStorage(storageFile)
                        : new CsvStorage(storageFile);

        // Repository (loads data automatically)
        GameRepository repository = new GameRepository(storage);

        System.out.println("Using storage file: " + storageFile);

        // Start application
        Menu menu = new Menu(repository);
        menu.handleMenu();
    }

    private static void validateArguments(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar boardgamecollection.jar <storage-file>");
            System.out.println("Storage file must be .json or .csv");
            System.exit(1);
        }

        String file = args[0];
        if (!file.endsWith(".json") && !file.endsWith(".csv")) {
            System.out.println("Error: Storage file must have .json or .csv extension");
            System.exit(1);
        }
    }
}
