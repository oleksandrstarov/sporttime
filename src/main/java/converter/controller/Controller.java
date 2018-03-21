package converter.controller;

import converter.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    Main mainApp;

    FileChooser fileChooser;

    @FXML
    private TextField portNumberFiled;

    @FXML
    private TextField fileAddressField;

    @FXML
    private Button startBtn;

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
    }

    @FXML
    private void selectFile() {
        File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage().getOwner());
        if (selectedFile != null) {
            fileAddressField.setText(selectedFile.getPath());
        }
    }

    @FXML
    private void startApp() {
        startBtn.setDisable(true);
        loader.setVisible(true);

    }

    @FXML
    private void exitApp() {
        System.exit(1);
    }

    private void validateInput() {
        startBtn.setDisable(portNumberFiled.getText().length() == 0 || fileAddressField.getText().length() == 0);
    }
}
