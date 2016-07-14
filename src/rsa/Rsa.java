package rsa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import util.TextUtils;

public final class Rsa {

    public static String encrypt(String rawText, PublicKey pub) {

        BigInteger message = TextUtils.textToAscii(rawText);

        BigInteger cipher = message.modPow(pub.e, pub.n);
        if (message.compareTo(pub.n) == 1) {
            throw new IllegalArgumentException("Message is too long!");
        }
        return cipher.toString();
    }

    public static String decrypt(String filename, PrivateKey pvt) throws IOException {
        BigInteger cipher = new BigInteger(TextUtils.readFromFile(filename));

        return TextUtils.asciiToText(cipher.modPow(pvt.d, pvt.n));
    }

    protected static BigInteger[] getKey(String file) {

        BigInteger[] values = new BigInteger[2];

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            for (int i = 0; (line = br.readLine()) != null; i++) {
                values[i] = new BigInteger(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return values;
    }
}
