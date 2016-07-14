package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import rsa.*;
import util.TextUtils;

final class ParseArguments {

    static void parse(String[] args) throws IOException {

        switch (args[0]) {
            case "-e": {
                PublicKey pub = new PublicKey(args[1]);
                String message;
                try {
                    message = TextUtils.readFromFile(args[2]);
                } catch (FileNotFoundException ex) {
                    message = args[2];
                }
                String cipher = Rsa.encrypt(message, pub);
                TextUtils.writeToFile("cipher.txt", cipher);
                break;
            }
            case "-d": {
                PrivateKey pvt = new PrivateKey(args[1]);

                String message = Rsa.decrypt(args[2], pvt);
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
