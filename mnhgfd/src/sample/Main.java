package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); //esta llama a sample que es un XML con el cual se
                                                                                    //maneja atributos de la ventana
        primaryStage.setTitle("Duck mail");
        primaryStage.setScene(new Scene(root, 382, 314));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
