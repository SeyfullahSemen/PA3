package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.Random;

public class Controller {
    @FXML
    private Button encryptNowButton;

    @FXML
    private TextField sentenceToText;

    @FXML
    private Label encryptedMessageLabel;

    @FXML
    private Label foundPrimeNumberLabel;

    @FXML
    private Label calculatedQLabel;

    // Dit is de zin die wij moeten encrypten
    private final String SENTENCE = "Good things come to people who wait, but better things come to those who go out and get them ~Anonymous";
    private String encryptedMessage = "";
    private static final Random random = new Random();
    private static final BigInteger INIT_NUMBER = new BigInteger("2");
    private static BigInteger p = INIT_NUMBER;
    private static BigInteger q;
    private static BigInteger e = new BigInteger("3");
    private static BigInteger modulus;
    private static BigInteger phi;

    private static BigInteger encoder(int message) {
        BigInteger messageConversion = new BigInteger("" + message);
        return messageConversion.modPow(e, modulus);
    }

    private void init(String N) {
        //Initialise n and p
        BigInteger n = new BigInteger(N);

        //For each prime p
        while (p.compareTo(n.divide(INIT_NUMBER)) <= 0) {

            //If we find p
            if (n.mod(p).equals(BigInteger.ZERO)) {
                //Calculate q
                q = n.divide(p);
                calculatedQLabel.setText(calculatedQLabel.getText() + " " + q);
                modulus = p.multiply(q);
                phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                return;
                //The end of the algorithm
            }
            //p = the next prime number
            p = p.nextProbablePrime();
            foundPrimeNumberLabel.setText(foundPrimeNumberLabel.getText() + " " + p);
        }
        System.out.println("There is no solution for this number.");
    }

    /**
     * Encrypt the message we have been given
     */
    private void encryptMessage() {
        long startTime = (int) System.nanoTime();
        init("21691");

        while (phi.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }

        String encryptedMessage = "";
        String[] sentenceArray = SENTENCE.split("");
        for (int i = 0; i < sentenceArray.length; i++) {
            for (int j = 0; j < sentenceArray[i].length(); j++) {
                char character = sentenceArray[i].charAt(j);
                int ascii = (int) character;
                encryptedMessage += " " + encoder(ascii);
            }
        }
        System.out.println(encryptedMessage);
        encryptedMessageLabel.setText(encryptedMessageLabel.getText() + " " + encryptedMessage);
    }

    /**
     * Call the encrypt message after mouse click on the
     * button to encrypt.
     */
    @FXML
    public void onEncryptButtonClick() {
        encryptNowButton.setOnMouseClicked(event -> encryptMessage());
    }

}
