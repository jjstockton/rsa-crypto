package rsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class RSA {

    public static void main(String[] args) throws IOException {

        if (args.length != 0) {

            switch (args[0]) {
                case "-e": {
                    PublicKey pub = PublicKey.getPublicKey(args[1]);
                    String message;
                    try {
                        message = Util.readFromFile(args[2]);
                    } catch (FileNotFoundException ex) {
                        message = args[2];
                    }
                    String cipher = encrypt(message, pub);
                    Util.writeToFile("cipher.txt", cipher);
                    break;
                }
                case "-d": {
                    PrivateKey priv = PrivateKey.getPrivateKey(args[1]);

                    String message = decrypt(args[2], priv);
                    System.out.println(message);
                    Util.writeToFile("decrypted.txt", message);
                    break;
                }
                case "-g": {
                    KeyPair keys = KeyPair.generateNewPair(args[1]);
                    break;
                }
                default:
                    System.out.println("Usage\n"
                            + "Encryption: -e <public key> <message>\n"
                            + "Decryption: -d <private key>\n"
                            + "Generate key pair: -g <name>");
                    break;
            }
        } else {
            //Put code here to run from editor
        }   
    }

    static String encrypt(String rawText, PublicKey k) {

        BigInteger message = Util.textToAscii(rawText);

        BigInteger e = k.e;
        BigInteger n = k.n;

        BigInteger cipher = message.modPow(e, n);
        if (message.compareTo(n) == 1) {
            throw new IllegalArgumentException("Message is too long!");
        }
        return cipher.toString();
    }

    static String decrypt(String filename, PrivateKey k) throws IOException {
        BigInteger cipher = new BigInteger(Util.readFromFile(filename));

        BigInteger d = k.d;
        BigInteger n = k.n;

        return Util.asciiToText(cipher.modPow(d, n));
    }
}
