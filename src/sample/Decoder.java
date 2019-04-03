package sample;

import java.math.BigInteger;

public class Decoder {
    /**
     * @param n              public key 1 (p*q)
     * @param e              public key 2 (inverse of e modulo φ)
     * @param encodedMessage message that has to be decoded
     * @return
     */
    public char[] decode(int n, int e, int[] encodedMessage) {
        System.out.println("\n===== Decoding ====");
        long startTime = System.currentTimeMillis();
        int[] pAndQ = calcPAndQ(n);
        if (!(pAndQ == null)) {

            int p = pAndQ[0];
            int q = pAndQ[1];
            int phi = (p - 1) * (q - 1);
            int d = modInverse(e, phi);

            System.out.println("n: " + n + "\np: " + p + "\nq: " + q + "\nφ (phi): " + phi + "\ne (co prime): " + e + "\nd (inverse): " + d + "\n");

            char[] decodedLetters = new char[encodedMessage.length];

            for (int i = 0; i < encodedMessage.length; i++) {
//                int letterCipher = BigInteger.valueOf(encodedMessage[i]).pow(d).mod(BigInteger.valueOf(n)).intValue();
                int letterCipher = BigInteger.valueOf(encodedMessage[i]).modPow(BigInteger.valueOf(d), BigInteger.valueOf(n)).intValue();
                decodedLetters[i] = (char) (letterCipher);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Decoding took: " + ((endTime - startTime) / 1000) + " seconds");
            System.out.println("Decoded message: " + new String(decodedLetters));
            return decodedLetters;
        }
        return null;
    }

    /**
     * Calculate p and q from p*q
     *
     * @param n
     * @return int[] p and q
     */
    public int[] calcPAndQ(int n) {

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
    public int nextPrime(int number) {
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
    public boolean isPrime(int number) {
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
    public int modInverse(int a, int m) {
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
    public int generateCoPrime(int number) {
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
    public void printCoPrimes(int number) {
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
    public int greatestCommonDivisor(int a, int b) {
        return b == 0 ? a : greatestCommonDivisor(b, a % b);
    }

}
