package util;

import java.math.BigInteger;
import java.util.Random;

public class NumberUtils {

    //Returns a prime number of bitlength length
    public static BigInteger generatePrime(int length) {
        Random rand = new Random();
        return BigInteger.probablePrime(length, rand);
    }

    //Random Integer between min and max (inclusive)
    public static int newRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
}
