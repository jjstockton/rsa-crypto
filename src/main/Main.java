package main;

import rsa.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            ParseArguments.parse(args);
        } else {
            //Sample usage
            //To encrypt a message
            PublicKey pub = new PublicKey("some_key.public.txt");
            String message = "A super secret message";
            if (message.length() <= pub.maxMessageLength()) {
                String cipher = Rsa.encrypt(message, pub);
            } else {
                System.out.println("The message is too long!");
            }

            //To decrypt a file
            PrivateKey pvt = new PrivateKey("some_key.private.txt");
            String decrypted = Rsa.decrypt("encrypted_file.txt", pvt);

            //To generate a new key pair
            KeyPair keys = new KeyPair("my_key");
            PublicKey myPub = keys.getPublicKey();
            PrivateKey myPvt = keys.getPrivateKey();
        }
    }
}
