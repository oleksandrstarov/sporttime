package converter;

import converter.controller.Controller;
import converter.service.FileWatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //createWatcher();
        System.out.println("watcher added");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ST>>>MeOS");

        initStage();
        initApplicationView();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initStage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/converterBase.fxml"));
            rootLayout = loader.load();

            primaryStage.setScene(new Scene(rootLayout, 300, 275));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initApplicationView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/converter.fxml"));
            Pane applicationView = loader.load();

            rootLayout.setCenter(applicationView);

            Controller controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void createWatcher() throws Exception{
        new FileWatcher( "D:\\Tests");
    }
}
