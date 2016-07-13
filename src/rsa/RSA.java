
package rsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class RSA {

    public static void main(String[] args) throws IOException {
        
        if(args.length != 0){

            switch (args[0]) {
                case "-e": {
                    PublicKey pub = PublicKey.getPublicKey(args[1]);
                    String message;
                    try{
                        message = Util.readFromFile(args[2]);
                    }catch(FileNotFoundException ex){
                        message = args[2];
                    }
                    BigInteger cipher = encrypt(message, pub);
                    break;
                }
                case "-d": {
                    PrivateKey priv = PrivateKey.getPrivateKey(args[1]);
                    
                    BigInteger cipher = new BigInteger(Util.readFromFile("cipher.txt"));
                    
                    String message = decrypt(cipher, priv);
                    System.out.println(message);
                    Util.writeToFile("message.txt", message);
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
        }else{
            //Run from editor
        }
    }

    static BigInteger encrypt(String rawText, PublicKey k) {

        BigInteger message = Util.textToAscii(rawText);

        BigInteger e = k.e;
        BigInteger n = k.n;
        
        BigInteger cipher = message.modPow(e, n);
        if(message.compareTo(n) == 1){
            throw new IllegalArgumentException("Message is too long!");
        }
        Util.writeToFile("cipher.txt", cipher.toString());

        return cipher;
    }

    static String decrypt(BigInteger cipher, PrivateKey k) {
        BigInteger d = k.d;
        BigInteger n = k.n;
        
        return Util.asciiToText(cipher.modPow(d, n));
    }
}
