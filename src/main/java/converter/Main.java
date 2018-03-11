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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/converter.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private FileWatcher createWatcher() throws Exception{
        return new FileWatcher( "D:\\Tests");
    }
}
