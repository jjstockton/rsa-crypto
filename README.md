# RSA Cryptography

Encrypt and decrypt messages using the RSA cryptosystem. Created as part of an optional assignment for ECE 103 (Discrete Mathematics) at the University of Waterloo.

### Sample Usage

```java
//To encrypt a message
PublicKey pub = PublicKey.getPublicKey("some_key.public.txt");
String message = "A super secret message";
if (message.length() <= pub.maxMessageLength()) {
	String cipher = encrypt(message, pub);
} else {
	System.out.println("The message is too long!");
}

//To decrypt a file
PrivateKey pvt = PrivateKey.getPrivateKey("my_key.private.txt");
String decrypted = decrypt("encrypted_file.txt", pvt);

//To generate a new key pair
KeyPair keys = KeyPair.generateNewPair("my_key");
PublicKey myPub = keys.getPublicKey();
PrivateKey myPvt = keys.getPrivateKey();
```

Alternatively, build and run from the command line:


```bash
$ java -jar RSA.jar -g <key_pair_name>
# Generates new key pair
$ java -jar RSA.jar -e <public_key> <message_to_encrypt>
# Encrypts message and places it in cipher.txt
$ java -jar RSA.jar -d <private_key> <encrypted_file>
> Decrypted message
# Decrypts message and places it in decrypted.txt
```
