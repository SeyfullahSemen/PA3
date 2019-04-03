package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.Random;

public class Controller {
    //////////////////{JEROEN}
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

    @FXML
    Button step3Button;
    /////////////////{JEROEN}


    @FXML
    private Button encryptNowButton;

    @FXML
    private TextField sentenceToText;

    @FXML
    private TextField inputNField;

    @FXML
    private TextField inputEField;

    @FXML
    private Button btnCalcDValue;

    @FXML
    private Label labelDValue;

    @FXML
    private TextField inputCField;

    @FXML
    private Button btnCalcD;

    @FXML
    private Label decryptedMessageLabel;

    @FXML
    private Label dd;

    @FXML
    private Label messageLabel;

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

    private static final int[] MESSAGE_TO_DECODE = {12628, 7932, 6411, 13036, 12669, 5421, 2770, 3242, 2441, 11622, 11234, 2770, 2441, 11235,
            7200, 2770, 7365, 3683, 5421, 7200, 2770, 8239, 2441, 11622, 2770, 7200, 7932, 2441, 5421,
            3683, 2770, 3242, 7932, 2441, 2770, 2763, 12687, 11234, 3683, 2770, 7200, 7932, 3683, 2770,
            7365, 3683, 5421, 7200, 2770, 2441, 8239, 2770, 7932, 2441, 3242, 2770, 7200, 7932, 6411,
            13036, 12669, 5421, 2770, 3242, 2441, 11622, 11234, 2770, 2441, 11235, 7200, 4475, 2770,
            87828, 75, 2441, 7932, 13036, 2770, 5204, 2441, 2441, 12439, 3683, 13036};

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
    public void onStep3ButtonClick() {
        step3Button.setOnMouseClicked(event -> encryptM());
    }

    @FXML
    public void onStep1ButtonClick() {
        step1Button.setOnMouseClicked(event -> findQandP());
    }

    @FXML
    public void onStep2ButtonClick() {
        step2Button.setOnMouseClicked(event -> calculateE());
    }

    @FXML
    public void onCalculateDButtonClick() {
        int nValue = Integer.parseInt(inputNField.getText());
        int eValue = Integer.parseInt(inputEField.getText());
        dd.setText("" + getD(nValue, eValue));

    }

    @FXML
    public void btnDecryptITClicked() {
        String cValue = inputCField.getText();
        String newest[] = cValue.split(",");
        System.out.println(newest.length);
        int messageArray[] = new int[newest.length];
        for (int i = 0; i < newest.length; i++) {
            messageArray[i] = Integer.parseInt(newest[i]);
        }
        decode(messageArray);
        inputCField.clear();
    }

    private char[] decode(int[] encodedMessage) {
        long startTime = System.currentTimeMillis();
        char[] decodedLetters = new char[encodedMessage.length];

        for (int i = 0; i < encodedMessage.length; i++) {
            int d = Integer.parseInt(dd.getText().trim());
            int n = Integer.parseInt(inputNField.getText());
            int letterCipher = BigInteger.valueOf(encodedMessage[i]).modPow(BigInteger.valueOf(d), BigInteger.valueOf(n)).intValue();
            decodedLetters[i] = (char) (letterCipher);
        }
        long endTime = System.currentTimeMillis();
        messageLabel.setText(new String(decodedLetters));
        System.out.println("Decoding took: " + ((endTime - startTime) / 1000) + " seconds");
        return decodedLetters;


    }

    /**
     * @param n public key 1 (p*q)
     * @param e public key 2 (inverse of e modulo φ)
     * @return
     */
    private int getD(int n, int e) {
        int[] pAndQ = calcPAndQ(n);
        if (!(pAndQ == null)) {

            int p = pAndQ[0];
            int q = pAndQ[1];
            int phi = (p - 1) * (q - 1);
            int d = modInverse(e, phi);

            return d;
        }
        return 0;
    }

    /**
     * Calculate p and q from p*q
     *
     * @param n
     * @return int[] p and q
     */
    private int[] calcPAndQ(int n) {

        //The first prime number
        int firstPrime = 2;

        //Initialise pq and p
        int p = firstPrime;

        //Loop through the prime numbers
        while (p < (n / firstPrime)) {

            //If pq modulo p equals zero we found p.
            if (n % p == 0) {

                //Calculate q
                int q = n / p;

                if (isPrime(q)) {
                    return new int[]{p, q};
                }

            }

            //Get the next prime number
            p = nextPrime(p);
        }
        //No solution found
        return null;
    }

    /**
     * Calculate the next prime number.
     *
     * @param number
     * @return
     */
    private int nextPrime(int number) {
        int nextPrime = -1;
        while (true) {
            if (isPrime(++number)) {
                nextPrime = number;
                break;
            }
        }
        return nextPrime;
    }

    /**
     * Calculate if n is a prime number.
     *
     * @param number
     * @return
     */
    private boolean isPrime(int number) {
        //check if n is a multiple of 2
        if (number % 2 == 0) {
            return false;
        }
        //if not, then just check the odds
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find modulor multiplicative inverse of @param a under modulo @param m
     *
     * @param a
     * @param m
     * @return
     */
    private int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    /**
     * Find the co prime for @param number
     *
     * @param number
     * @return
     */
    private int generateCoPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (greatestCommonDivisor(number, i) == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Print all co primes for @param number
     *
     * @param number
     */
    private void printCoPrimes(int number) {
        for (int i = 2; i < number; i++) {
            if (greatestCommonDivisor(number, i) == 1) {
                System.out.print(i + " ");
            }
        }
    }

    /**
     * Find the greatest common divisor for @param a and @param b
     *
     * @param a
     * @param b
     * @return
     */
    private int greatestCommonDivisor(int a, int b) {
        return b == 0 ? a : greatestCommonDivisor(b, a % b);
    }


}




