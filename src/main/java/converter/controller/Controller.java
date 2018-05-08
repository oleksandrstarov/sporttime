package converter.controller;

import converter.Main;
import converter.service.Log;
import converter.service.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;

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
        try {
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
        } catch (Exception e) {
            Log.getInstance().error(e);
        } catch (Error e) {
            Log.getInstance().error(e);
        }
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
        try {
            Log.getInstance().log("Application started");

            startBtn.setDisable(true);
            loader.setVisible(true);
            selectFileBtn.setDisable(true);

            portNumberFiled.setDisable(true);
            fileAddressField.setDisable(true);

            Settings.getInstance().setPort(portNumberFiled.getText());
            Settings.getInstance().setPath(selectedFile.getParent());
            Settings.getInstance().setFileName(selectedFile.getName());
            mainApp.createWatcher();

        } catch (Exception e) {
            Log.getInstance().error(e);
        } catch (Error e) {
            Log.getInstance().error(e);
        }

    }

    @FXML
    private void exitApp() {
        Log.getInstance().log("Application stopped by user");
        System.exit(1);
    }

    @FXML
    private void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О приложени");
        alert.setHeaderText("Приложение для ковертации данных ST в приграмму MeOS");
        alert.setContentText(
                "1.Запустите KuKuD.exe\n" +
                "2.Проверьте, что путь к файлу совпадает\n" +
                "3.Запустите MeOS и установите соответствующий порт для TCP соединения\n" +
                "4.При считывании, данные из чипа автоматически появятся в MeOS (тестировалось на версии 3.4, 3.5)\n" +
                "5.Предусмотрен режим работы быстрая заявка и считывание\n" +
                "5.Если система не отвечает - файл лога доступен в папке с этим приложением, отправьте его разработчику\n" +
                "6.Конвертер создан для удобства при проведении тренировок," +
                        " ответственность за достоверность результатов лежит на судейской коллегии\n" +
                "\n " +
                "oleksandr.starov v.0.0.2"
        );

        alert.showAndWait();
    }

    private void validateInput() {
        startBtn.setDisable(portNumberFiled.getText().length() == 0 || fileAddressField.getText().length() == 0);
    }
}
