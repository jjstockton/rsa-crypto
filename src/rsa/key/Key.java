package rsa.key;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

class Key {

    protected final BigInteger n;

    protected Key(BigInteger n) {
        this.n = n;
    }

    protected static BigInteger[] getKey(String file) {

        BigInteger[] values = new BigInteger[2];

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            for (int i = 0; (line = br.readLine()) != null; i++) {
                values[i] = new BigInteger(line);
            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return values;
    }
}
