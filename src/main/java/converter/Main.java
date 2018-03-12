package converter;

import converter.service.FileWatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        createWatcher();
        System.out.println("watcher added");
        initStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initStage(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/converter.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createWatcher() throws Exception{
        new FileWatcher( "D:\\Tests");
    }
}
