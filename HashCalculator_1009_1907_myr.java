// 代码生成时间: 2025-10-09 19:07:39
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class HashCalculator {

    private static final String SHA_256 = "SHA-256";

    /**
     * Calculates the SHA-256 hash of the given string.
     *
     * @param input The string to be hashed.
     * @return The SHA-256 hash as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    
    public static String calculateSHA256Hash(String input) throws NoSuchAlgorithmException {
        // Get a SHA-256 digest instance
        MessageDigest digest = MessageDigest.getInstance(SHA_256);
        
        // Apply the digest to the input string
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        
        // Convert the byte array into a hexadecimal string
        BigInteger number = new BigInteger(1, hash);
        String hexString = number.toString(16);
        
        // Add leading zeroes if necessary
        while (hexString.length() < 32) {
            hexString = "0" + hexString;
        }
        
        return hexString;
    }

    public static void main(String[] args) {
        try {
            String input = "Hello, World!"; // Example input
            String hash = calculateSHA256Hash(input);
            System.out.println("SHA-256 hash of 'Hello, World!' is: " + hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not found: " + e.getMessage());
        }
    }
}
