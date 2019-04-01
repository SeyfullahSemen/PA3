package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.Random;

public class Controller {

    @FXML
    private Label sentenceM;

    @FXML
    private Button step1Button;

    @FXML
    private Button step2Button;

    @FXML
    private TextField nField;

    @FXML
    private Label encryptedMessageLabel;

    @FXML
    private Label foundPrimeNumberLabel;

    @FXML
    private Label calculatedQLabel;

    @FXML
    private Label calculationTime;

    @FXML
    private Label calculatedELabel;

    @FXML
    private TextField sentenceField;

    @FXML Button step3Button;

    // Dit is de zin die wij moeten encrypten
    private final String SENTENCE = "Good things come to people who wait, but better things come to those who go out and get them ~Anonymous";
    private String encryptedMessage = "";
    private static final Random random = new Random();
    private static final BigInteger INIT_NUMBER = new BigInteger("2");
    private static final String Q_IS = "q is ";
    private static final String P_IS = "p is ";
    private static final String TIME_FINDING_QP = "Amount of time busy finding p and q: ";
    private static final String E_IS = "e is ";
    private static final String MESSAGE_C_IS = "Message after encryption is: ";
    private BigInteger p = INIT_NUMBER;
    private BigInteger q;
    private BigInteger e = new BigInteger("3");
    private BigInteger modulus;
    private BigInteger phi;

    @FXML
    public void initialize() {
        sentenceField.setText(SENTENCE);
    }

    private BigInteger encoder(int message) {
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
                calculatedQLabel.setText("");
                calculatedQLabel.setText(Q_IS + " " + q);
                modulus = p.multiply(q);
                phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                foundPrimeNumberLabel.setText(P_IS + " " + p);
                return;
                //The end of the algorithm
            }
            //p = the next prime number
            p = p.nextProbablePrime();
        }
        System.out.println("There is no solution for this number.");
    }

    /**
     * Encrypt the message we have been given
     */
    private void findQandP() {
        p = new BigInteger("2");
        q = new BigInteger("0");
        e = new BigInteger("3");
        long startTime = System.currentTimeMillis();
        init(nField.getText());
        calculationTime.setText(TIME_FINDING_QP + (System.currentTimeMillis() - startTime) + " ms");

    }

    private void calculateE() {
        while (phi.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }

        calculatedELabel.setText(E_IS + " " + e);
    }

    private void encryptM() {
        String encryptedMessage = "";
        String[] sentenceArray = sentenceField.getText().split("");
        for (int i = 0; i < sentenceArray.length; i++) {
            if (i % 16 == 0) {
                encryptedMessage += "\n";
            }
            for (int j = 0; j < sentenceArray[i].length(); j++) {
                char character = sentenceArray[i].charAt(j);
                int ascii = (int) character;
                encryptedMessage += " " + encoder(ascii);
            }
        }
        System.out.println(encryptedMessage);
        encryptedMessageLabel.setText(MESSAGE_C_IS + "\n" + encryptedMessage);
    }

    /**
     * Call the encrypt message after mouse click on the
     * button to encrypt.
     */
    @FXML
    public void onStep1ButtonClick() {
        step1Button.setOnMouseClicked(event -> findQandP());
    }

    @FXML
    public void onStep2ButtonClick() {
        step2Button.setOnMouseClicked(event -> calculateE());
    }

    @FXML
    public void onStep3ButtonClick() {
        step3Button.setOnMouseClicked(event -> encryptM());
    }

}
