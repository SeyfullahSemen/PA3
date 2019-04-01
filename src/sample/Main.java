package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO: n = 21691, verzin een e=.. neem wel een klein getal hiervoor is makkelijker.
        //TODO: zin om te encrypten = Good things come to people who wait, but better things come to those who go out and get them ~Anonymous
        /**
         * TODO: getallen reeks die wij moeten decrypten:
         * TODO: [12628,7932,6411,13036,12669,5421,2770,3242,2441,11622,11234,2770,2441,11235,
         * TODO: 7200,2770,7365,3683,5421,7200,2770,8239,2441,11622,2770,7200,7932,2441,5421,
         * TODO: 3683,2770,3242,7932,2441,2770,2763,12687,11234,3683,2770,7200,7932,3683,2770,
         * TODO: 7365,3683,5421,7200,2770,2441,8239,2770,7932,2441,3242,2770,7200,7932,6411,
         * TODO: 13036,12669,5421,2770,3242,2441,11622,11234,2770,2441,11235,7200,4475,2770,
         * TODO: 87828,75,2441,7932,13036,2770,5204,2441,2441,12439,3683,13036]
         *
         * TODO: De gegeven public key: 13493,7------> N= 13493 E=7
         */
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("RSA encryptor/decryptor");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
