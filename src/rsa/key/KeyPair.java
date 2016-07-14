package rsa.key;

import java.math.BigInteger;
import util.*;

public class KeyPair {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyPair(PublicKey pub, PrivateKey priv) {
        this.publicKey = pub;
        this.privateKey = priv;
    }

    public KeyPair(String name) {

        BigInteger p = NumberUtils.generatePrime(NumberUtils.newRandom(1000, 2000));
        BigInteger q = NumberUtils.generatePrime(NumberUtils.newRandom(1000, 2000));

        BigInteger n = p.multiply(q);

        BigInteger o = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;

        int pow = NumberUtils.newRandom(1000, 2000);
        for (BigInteger i = BigInteger.valueOf(2).pow(pow); true; i = i.add(BigInteger.ONE)) {
            if (o.gcd(i).equals(BigInteger.ONE)) {
                e = i;
                break;
            }
        }

        BigInteger d = e.modInverse(o);

        PublicKey pub = new PublicKey(e, n);
        PrivateKey priv = new PrivateKey(d, n);

        TextUtils.writeToFile(name + ".public.txt", pub.e + "\r\n" + pub.n);
        TextUtils.writeToFile(name + ".private.txt", priv.d + "\r\n" + priv.n);

        this.publicKey = pub;
        this.privateKey = priv;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
