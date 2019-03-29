package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private Button encryptNowButton;

    @FXML
    private TextField sentenceToText;

    // Dit is de zin die wij moeten encrypten
    private final String SENTENCE = "Good things come to people who wait, but better things come to those who go out and get them ~Anonymous";
    private final Long N_FOR_ENCRYPTION = 21691L; // De N die wij hebben gekregen van Anke
    private final int E_FOR_ENCRYPTION = 3; // De E die wij zelf hebben uitgekozen.
    




}
