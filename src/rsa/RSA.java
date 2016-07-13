package rsa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;


//TODO:
//Fix classes
//Methods for writing/reading file

public class RSA {

    public static void main(String[] args) throws IOException {

        switch (args[0]) {
            case "-e":
                {
                    PublicKey pub = PublicKey.getPublicKey(args[1]);
                    String message;
                    try{
                        message = readFromFile(args[2]);
                    }catch(FileNotFoundException ex){
                        //System.out.println("in here");
                        message = args[2];
                    }
                    BigInteger cipher = encrypt(message, pub);
                    break;
                }
            case "-d":
                {
                    System.out.println("decrypting...");
                    PrivateKey priv = PrivateKey.getPrivateKey(args[1]);
                    BigInteger cipher;
                    try (BufferedReader br = new BufferedReader(new FileReader("cipher.txt"))) {
                        cipher = new BigInteger(br.readLine());
                    }       
                    String message = decrypt(cipher, priv);
                    System.out.println(message);
                    writeToFile("message.txt", message);
                    break;
                }
            case "-g":
                KeyPair keys = KeyPair.generateNewPair(args[1]);
                break;
        }

    }

    static BigInteger encrypt(String rawText, PublicKey k) {

        BigInteger message = textToAscii(rawText);

        BigInteger e = k.e;
        BigInteger n = k.n;
        
        BigInteger cipher = message.modPow(e, n);
        if(message.compareTo(n) == 1){
            throw new IllegalArgumentException("Message is too long!");
        }
        writeToFile("cipher.txt", cipher.toString());


        return cipher;

    }

    static String decrypt(BigInteger cipher, PrivateKey k) {

        BigInteger d = k.d;
        BigInteger n = k.n;

        return asciiToText(cipher.modPow(d, n));

    }

    public static String asciiToText(BigInteger m) {

        String text = "";

        while (m.compareTo(BigInteger.ONE) >= 0) {
            char c = (char) m.mod(BigInteger.valueOf(1000)).intValue();

            text = c + text;

            m = m.divide(BigInteger.valueOf(1000L));
        }

        return text;

    }

    public static BigInteger textToAscii(String text) {

        String message = "";

        for (char c : text.toCharArray()) {

            int ascii = c;
            message += c < 100 && !message.equals("") ? "0" + Integer.toString(ascii) : Integer.toString(ascii);

        }
        return new BigInteger(message);
    }
    
    public static void writeToFile(String fileName, String text) {
      
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.println(text);
            writer.close();
        }catch(FileNotFoundException | UnsupportedEncodingException ex){
            System.err.println(ex.getMessage());
        }
        
    }
    
    public static String readFromFile(String filename) throws IOException {
        
        String text = "";
       
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
            while((line = br.readLine()) != null){
            text += line + "\n";
        }
        
        return text;
    }
    
}

final class Number {

    public static BigInteger generatePrime(int length) {

        Random rand = new Random();

        return BigInteger.probablePrime(length, rand);
    }
    
    public static int newRandom(int min, int max) {
        
        Random rand = new Random();
        
        return rand.nextInt(max - min + 1) + min;
    }

}

class Key {
    

    protected final BigInteger n;
    
    public Key(BigInteger n) {
        this.n = n;
    }

    public static BigInteger[] getKey(String file){
        
        BigInteger[] values = new BigInteger[2];
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            
            String line;
            for(int i = 0; (line = br.readLine()) != null; i++){
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

class PublicKey extends Key {

    public final BigInteger e;

    public PublicKey(BigInteger e, BigInteger n) {
        super(n);
        this.e = e;
    }

    public static PublicKey getPublicKey(String file) {
   
        BigInteger[] values = getKey(file);
        BigInteger e = values[0];
        BigInteger n = values[1];
        
        return new PublicKey(e, n);
        
    }

}

class PrivateKey extends Key {

    public final BigInteger d;

    public PrivateKey(BigInteger d, BigInteger n) {

        super(n);
        this.d = d;
       
    }

    public static PrivateKey getPrivateKey(String file) throws FileNotFoundException, IOException {

        BigInteger[] values = getKey(file);
        BigInteger d = values[0];
        BigInteger n = values[1];
        
        return new PrivateKey(d, n);
    }

}

class KeyPair {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyPair(PublicKey pub, PrivateKey priv) {
        this.publicKey = pub;
        this.privateKey = priv;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
    
    
    public static KeyPair generateNewPair(String keyName) throws FileNotFoundException, UnsupportedEncodingException {

        BigInteger p = Number.generatePrime(Number.newRandom(1000, 2000));
        BigInteger q = Number.generatePrime(Number.newRandom(1000, 2000));

        BigInteger n = p.multiply(q);

        BigInteger o = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;

        int pow = Number.newRandom(1000, 2000);

        for (BigInteger i = BigInteger.valueOf(2).pow(pow) ; true ; i = i.add(BigInteger.ONE)) {
       
            if (o.gcd(i).equals(BigInteger.ONE)) {
                e = i;
                break;
            }
        }

        BigInteger d = e.modInverse(o);

        PublicKey pub = new PublicKey(e, n);

        PrivateKey priv = new PrivateKey(d, n);
                
        PrintWriter writer = new PrintWriter(keyName + "_pub.txt", "UTF-8");
        writer.println(pub.e);
        writer.println(pub.n);
        writer.close();

        writer = new PrintWriter(keyName + "_private.txt", "UTF-8");
        writer.println(priv.d);
        writer.println(priv.n);
        writer.close();

        return new KeyPair(pub, priv);

    }
}
