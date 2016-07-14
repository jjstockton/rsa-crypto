package rsa.key;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import util.TextUtils;

public class PrivateKey extends Key {

    protected final BigInteger d;

    protected PrivateKey(BigInteger d, BigInteger n) {
        super(n);
        this.d = d;
    }

    public static PrivateKey getPrivateKey(String file) throws FileNotFoundException, IOException {

        if (!file.contains(".private.txt")) {
            file += ".private.txt";
        }

        BigInteger[] values = getKey(file);
        BigInteger d = values[0];
        BigInteger n = values[1];

        return new PrivateKey(d, n);
    }

    public String decrypt(String filename) throws IOException {
        BigInteger cipher = new BigInteger(TextUtils.readFromFile(filename));

        return TextUtils.asciiToText(cipher.modPow(d, n));
    }
}
