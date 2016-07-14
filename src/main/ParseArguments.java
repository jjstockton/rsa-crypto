package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import rsa.key.KeyPair;
import rsa.key.PrivateKey;
import rsa.key.PublicKey;
import util.TextUtils;

class ParseArguments {

    static void parse(String[] args) throws IOException {

        switch (args[0]) {
            case "-e": {
                PublicKey pub = PublicKey.getPublicKey(args[1]);
                String message;
                try {
                    message = TextUtils.readFromFile(args[2]);
                } catch (FileNotFoundException ex) {
                    message = args[2];
                }
                String cipher = pub.encrypt(message);
                TextUtils.writeToFile("cipher.txt", cipher);
                break;
            }
            case "-d": {
                PrivateKey pvt = PrivateKey.getPrivateKey(args[1]);

                String message = pvt.decrypt(args[2]);
                System.out.println(message);
                TextUtils.writeToFile("decrypted.txt", message);
                break;
            }
            case "-g": {
                KeyPair keys = new KeyPair(args[1]);
                break;
            }
            default:
                System.out.println("Usage\n"
                        + "Encryption: -e <public key> <message>\n"
                        + "Decryption: -d <private key>\n"
                        + "Generate key pair: -g <name>");
                break;
        }
    }
}
