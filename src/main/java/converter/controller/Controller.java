package converter.controller;

import converter.Main;
import converter.service.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Controller {
    private Main mainApp;
    private FileChooser fileChooser;
    private File selectedFile;

    @FXML
    private TextField portNumberFiled;

    @FXML
    private TextField fileAddressField;

    @FXML
    private Button startBtn;

    @FXML
    private Button selectFileBtn;

    @FXML
    private ProgressIndicator loader;

    public Controller() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        loader.setVisible(false);

        System.out.println("init");
        portNumberFiled.setText("10000");
        fileAddressField.setText("");
        validateInput();
        fileAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            validateInput();
        });

        portNumberFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            validateInput();
        });

        portNumberFiled.addEventFilter(KeyEvent.KEY_TYPED, (event) -> {
            char ar[] = event.getCharacter().toCharArray();
            char ch = ar[event.getCharacter().toCharArray().length - 1];
            if (!(ch >= '0' && ch <= '9')) {
                event.consume();
            }
        });
    }

    @FXML
    private void selectFile() {
        selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage().getOwner());
        if (selectedFile != null) {
            fileAddressField.setText(selectedFile.getPath());
        }
    }

    @FXML
    private void startApp() {
        startBtn.setDisable(true);
        loader.setVisible(true);
        selectFileBtn.setDisable(true);

        portNumberFiled.setDisable(true);
        fileAddressField.setDisable(true);

        Settings.getInstance().setPort(portNumberFiled.getText());
        Settings.getInstance().setPath(selectedFile.getParent());
        Settings.getInstance().setFileName(selectedFile.getName());

        try {
            mainApp.createWatcher();
        } catch(Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void exitApp() {
        System.exit(1);
    }

    @FXML
    private void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О приложени");
        alert.setHeaderText("Приложение для ковертации данных ST в приграмму MeOS");
        alert.setContentText("1.Запустите KuKuD.exe\n2.Проверьте, что путь к файлу совпадает\n3.Запустите MeOS и установите соответствующий порт для TCP соединения\n\n oleksandr.starov v.0.0.1");

        alert.showAndWait();
    }

    private void validateInput() {
        startBtn.setDisable(portNumberFiled.getText().length() == 0 || fileAddressField.getText().length() == 0);
    }

    private void showErrorDialog(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Exception in converter to MeOS");
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
