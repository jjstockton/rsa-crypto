package rsa;

import java.math.BigInteger;

public class PrivateKey {

    protected final BigInteger d;
    protected final BigInteger n;

    protected PrivateKey(BigInteger d, BigInteger n) {
        this.n = n;
        this.d = d;
    }
    
    public PrivateKey(String file) {
        
        if (!file.contains(".private.txt")) {
            file += ".private.txt";
        }

        BigInteger[] values = Rsa.getKey(file);
        BigInteger d = values[0];
        BigInteger n = values[1];

        this.d = d;
        this.n = n;
        
        
    }
}
