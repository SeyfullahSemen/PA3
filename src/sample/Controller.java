package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.Random;

public class Controller {
    @FXML
    private Button encryptNowButton;

    @FXML
    private TextField sentenceToText;

    // Dit is de zin die wij moeten encrypten
    private final String SENTENCE = "Good things come to people who wait, but better things come to those who go out and get them ~Anonymous";
    //private final Long N_FOR_ENCRYPTION = 21691L; // De N die wij hebben gekregen van Anke
    //private final int E_FOR_ENCRYPTION = 3; // De E die wij zelf hebben uitgekozen.
    private static final Random random = new Random();
    private static final BigInteger INIT_NUMBER = new BigInteger("21691");
    private static BigInteger p = INIT_NUMBER;
    private static BigInteger q;
    private static BigInteger e = new BigInteger("3");
    private static BigInteger modulus;
    private static BigInteger phi;
    long startTime = (int) System.nanoTime();





}
