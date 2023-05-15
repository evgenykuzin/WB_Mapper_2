package ru.jekajops.wbtablemapper.services.googlesheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import ru.jekajops.wbtablemapper.services.googlesheets.exception.InitiationException;
import ru.jekajops.wbtablemapper.services.util.DataManager;
import ru.jekajops.wbtablemapper.util.Constants;
import ru.jekajops.wbtablemapper.util.FileManager;
import ru.jekajops.wbtablemapper.util.Table;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class GoogleSheetsManager<T> implements DataManager {
    private static final String APPLICATION_NAME = "Parser";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final int GID = 0;
    public static final String userId = "user3";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private Sheets service;
    private String majorDimension;
    private String range;
    private String credentialsFilePath;
    private Comparator<Table.Row> comparator;
    private String spreadsheetId;
    private String tableIdColName;
    protected BiFunction<List<String>, Collection<List<Object>>, Table> tableGenerator;
//    protected final Consumer<Table> beforeTableConsumer;
//    protected final Consumer<Table> afterTableConsumer;

    public GoogleSheetsManager(
            String credentialsFilePath,
            String spreadsheetId,
            String tableIdColName,
            Comparator<Table.Row> comparator,
            BiFunction<List<String>, Collection<List<Object>>, Table> tableGenerator
//            Consumer<Table> beforeTableConsumer,
//            Consumer<Table> afterTableConsumer
    ) throws InitiationException {
        init(credentialsFilePath, spreadsheetId, tableIdColName, comparator, tableGenerator);
    }

    protected void init(
            String credentialsFilePath,
            String spreadsheetId,
            String tableIdColName,
            Comparator<Table.Row> comparator,
            BiFunction<List<String>, Collection<List<Object>>, Table> tableGenerator
    ) throws InitiationException {
        this.credentialsFilePath = credentialsFilePath;
        this.spreadsheetId = spreadsheetId;
        this.comparator = comparator;
        this.tableIdColName = tableIdColName;
        this.tableGenerator = tableGenerator;
//        this.beforeTableConsumer = beforeTableConsumer;
//        this.afterTableConsumer = afterTableConsumer;
        final NetHttpTransport HTTP_TRANSPORT;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            boolean configured = false;
            while (!configured) {
                try {
                    service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                            .setApplicationName(APPLICATION_NAME)
                            .build();
                    configured = true;
                } catch (RuntimeException e) {
                    System.out.println("accessing google-api...");
                    try {
                        wait(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            range = getRange();
        } catch (GeneralSecurityException | IOException e) {
            throw new InitiationException(e);
        }
    }

    public GoogleSheetsManager(
            String credentialsFilePath,
            String spreadsheetId,
            String tableIdColName,
            Comparator<Table.Row> comparator,
            Consumer<Table> beforeTableConsumer,
            Consumer<Table> afterTableConsumer
    ) throws InitiationException {
        this(
                credentialsFilePath,
                spreadsheetId,
                tableIdColName,
                comparator,
                tableBiFunction(tableIdColName, beforeTableConsumer, afterTableConsumer)
        );
    }

//    public GoogleSheetsManager(
//            String credentialsFilePath,
//            String spreadsheetId,
//            String tableIdColName,
//            Comparator<Table.Row> comparator,
//            Consumer<Table.Row> beforeRowsConsumer,
//            Consumer<Table.Row> afterRowsConsumer
//    ) throws InitiationException {
//        this(
//                credentialsFilePath,
//                spreadsheetId,
//                tableIdColName,
//                comparator,
//                tableBiFunction(tableIdColName, beforeRowsConsumer, afterRowsConsumer),
//                t->{}, t->{}
//        );
//    }

    public GoogleSheetsManager(String credentialsFilePath, String spreadsheetId, String tableIdColName) throws InitiationException {
        this(credentialsFilePath, spreadsheetId, tableIdColName, null, row -> {
        }, row -> {
        });
    }

    protected static BiFunction<List<String>, Collection<List<Object>>, Table> tableBiFunction(String tableIdColName) {
        return tableBiFunction(tableIdColName, t -> {}, t -> {});
    }

    protected static BiFunction<List<String>, Collection<List<Object>>, Table> tableBiFunction(String tableIdColName, Consumer<Table> beforeTableConsumer, Consumer<Table> afterTableConsumer) {
        return tableBiFunction(tableIdColName, row -> {}, row -> {}, beforeTableConsumer, afterTableConsumer);
    }

    protected static BiFunction<List<String>, Collection<List<Object>>, Table> tableBiFunction(String tableIdColName, Consumer<Table.Row> beforeRowsConsumer, Consumer<Table.Row> afterRowsConsumer, Consumer<Table> beforeTableConsumer, Consumer<Table> afterTableConsumer) {
        return (keys, values) -> new Table(
                tableIdColName,
                keys,
                values,
                beforeRowsConsumer,
                afterRowsConsumer,
                beforeTableConsumer,
                afterTableConsumer
        );
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, InitiationException {
        // Load client secrets.
        try (InputStream is = getCredentialsInputStream()) {
            return GoogleCredential.fromStream(is)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
//            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(is));
//            // Build flow and trigger user authorization request.
//            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
//                    .setAccessType("offline")
//                    .build();
//            LocalServerReceiver receiver = new LocalServerReceiver
//                    .Builder()
//                    .setPort(8080)
//                    .build();
//            return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
        } catch (Exception e) {
            throw new InitiationException(e);
        }
    }

    private InputStream getCredentialsInputStream() {
        try {
            return new FileInputStream(FileManager.getFromResources(credentialsFilePath));
        } catch (FileNotFoundException fnfe) {
            System.out.println("credentials.json not found. Getting credentials.json from env... = ");
            try {
                return new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            } catch (FileNotFoundException e) {
                System.out.println("~/env/credentials.json not found. Getting credentials content from env... = ");
                return new ByteArrayInputStream(System.getenv("GOOGLE_CREDENTIALS").getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private ValueRange getValueRange(String range) throws IOException {
        return service.spreadsheets()
                .values()
                .get(spreadsheetId, range)
                .execute();
    }

    private String getRange(int size) throws IOException {
        return getRange(Constants.FIRST_ROW_EXEL_INDEX, size);
    }

    private String getRange(Integer atIndex, int toIndex) throws IOException {
        return String.format("A%s:AE%d", atIndex, toIndex);
    }

    private String getAllRangeWithoutKeys(int size) throws IOException {
        return String.format("A%s:AE%d", Constants.FIRST_ROW_EXEL_INDEX, size);
    }

    private String getRange() throws IOException {
        var r = service.spreadsheets()
                .get(spreadsheetId)
                .execute()
                .getSheets().get(0)
                .getProperties()
                .getGridProperties()
                .getRowCount();
        return getRange(r * 2);
    }

    private int getNextIndex(List<List<Object>> values) {
        return values.stream()
                .filter(objects -> !objects.isEmpty())
                .filter(objects -> objects.get(0) != null && !objects.get(0).toString().isEmpty())
                .map(objects -> Integer.parseInt(objects.get(0).toString()))
                .max(Integer::compare)
                .orElseGet(() -> new Random().nextInt((int) (values.size() * 1.5)));
    }

    private void putIfEmpty(Table.Row row, String key, String value) {
        if (row.get(key) == null || row.get(key).isEmpty()) {
            System.out.println("row = " + row);
            System.out.println("value = " + value);
            row.put(key, value);
        }
    }

    @Override
    public Table parseTable() {
        ValueRange response = null;
        try {
            response = getValueRange(range);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) return Table.getEmptyTable();
        majorDimension = response.getMajorDimension();
        List<List<Object>> values = Optional.ofNullable(response.getValues()).orElse(Collections.emptyList());
        var keys = getKeys(values);
        return tableGenerator.apply(keys, values);
    }

    @Override
    public void writeTable(Table table, Integer atIndex) {
        try {
            ValueRange valueRange = new ValueRange();
            var range = getRange(atIndex, atIndex + table.size() + 5);
            valueRange.setRange(range);
            valueRange.setMajorDimension(majorDimension);
            List<List<Object>> values;
            if (comparator != null) {
                values = table.getValuesMatrix(comparator);
            } else {
                values = table.getValuesMatrix();
            }

            //values.remove(0);
            valueRange.setValues(values.stream().map(l -> l.stream().map(o -> {
                if (o == null || o.equals("null")) {
                    return "";
                }
                return o;
            }).collect(Collectors.toList())).collect(Collectors.toList()));

            valueRange.setMajorDimension("ROWS");
            service.spreadsheets().values()
                    .update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTableHead(String head, Integer atIndex, Integer toIndex) {
        try {
            ValueRange valueRange = new ValueRange();
            var range = getRange(atIndex, atIndex+toIndex+5);
            valueRange.setRange(range);
            valueRange.setMajorDimension(majorDimension);
            //values.remove(0);
            valueRange.put("B"+atIndex, head);
            valueRange.setMajorDimension("ROWS");

            service.spreadsheets().values()
                    .update(spreadsheetId, range, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getLastRowIndex() {
        try {
            return service.spreadsheets()
                    .values()
                    .get(spreadsheetId, range)
                    .execute()
                    .getValues()
                    .size() + Constants.FIRST_ROW_EXEL_INDEX;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public List<String> getKeys(List<List<Object>> data) {
        return defaultGetKeys(data);
    }

    public abstract List<T> parseModels();

}