package converter.controller;

import converter.Main;
import javafx.fxml.FXML;

public class Controller {
    Main mainApp;


    public Controller() {

    }

    @FXML
    private void initialize() {
        System.out.println("init");
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
