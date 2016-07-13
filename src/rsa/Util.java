package rsa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;

public final class Util {

    //Returns a prime number of bitlength length
    public static BigInteger generatePrime(int length) {
        Random rand = new Random();
        return BigInteger.probablePrime(length, rand);
    }

    //Random Integer between min and max (inclusive)
    public static int newRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    //Converts Ascii code to text
    public static String asciiToText(BigInteger m) {

        String text = "";

        while (m.compareTo(BigInteger.ONE) >= 0) {
            char c = (char) m.mod(BigInteger.valueOf(1000)).intValue();
            text = c + text;
            m = m.divide(BigInteger.valueOf(1000L));
        }
        return text;
    }

    //Converts text to ascii code
    public static BigInteger textToAscii(String text) {
        String message = "";
        for (char c : text.toCharArray()) {
            int ascii = c;
            message += c < 100 && !message.equals("") ? "0" + Integer.toString(ascii) : Integer.toString(ascii);
        }
        return new BigInteger(message);
    }

    //Writes text to file
    public static void writeToFile(String fileName, String text) {

        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.err.println(ex.getMessage());
        }
    }

    //Reads text from file
    public static String readFromFile(String filename) throws IOException {
        String text = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            text += line + "\r\n";
        }
        text = text.substring(0, text.length() - 2);
        return text;
    }
}
