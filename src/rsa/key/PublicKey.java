package rsa.key;

import java.math.BigInteger;
import util.TextUtils;

public class PublicKey extends Key {

    protected final BigInteger e;

    protected PublicKey(BigInteger e, BigInteger n) {
        super(n);
        this.e = e;
    }

    public static PublicKey getPublicKey(String file) {

        if (!file.contains(".public.txt")) {
            file += ".public.txt";
        }

        BigInteger[] values = getKey(file);
        BigInteger e = values[0];
        BigInteger n = values[1];

        return new PublicKey(e, n);
    }

    public int maxMessageLength() {
        return (int) Math.floor((this.n).toString().length() / 3);
    }

    public String encrypt(String rawText) {

        BigInteger message = TextUtils.textToAscii(rawText);
        
        BigInteger cipher = message.modPow(e, n);
        if (message.compareTo(n) == 1) {
            throw new IllegalArgumentException("Message is too long!");
        }
        return cipher.toString();
    }
}
