package rsa;

import java.math.BigInteger;

public class PublicKey {

    protected final BigInteger e;
    protected final BigInteger n;

    protected PublicKey(BigInteger e, BigInteger n) {
        this.n = n;
        this.e = e;
    }
    
    public PublicKey(String file) {
        
         if (!file.contains(".public.txt")) {
            file += ".public.txt";
        }

        BigInteger[] values = Rsa.getKey(file);
        BigInteger e = values[0];
        BigInteger n = values[1];

        this.e = e;
        this.n = n;
    }

    public int maxMessageLength() {
        return (int) Math.floor((this.n).toString().length() / 3);
    }
}
