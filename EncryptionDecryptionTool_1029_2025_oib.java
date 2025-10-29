// 代码生成时间: 2025-10-29 20:25:20
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionDecryptionTool {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;

    public static Key deriveKey(String password) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), "salt".getBytes(), ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
        return factory.generateSecret(spec);
    }

    public static byte[] encrypt(File file, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM));

        byte[] iv = new byte[16]; // AES uses 16 bytes IV
        FileInputStream fis = new FileInputStream(file);
        byte[] fileContent = new byte[(int) file.length()];
        fis.read(fileContent);
        fis.close();

        byte[] encryptedData = cipher.doFinal(fileContent);
        byte[] result = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);
        return result;
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[16];
        System.arraycopy(data, 0, iv, 0, iv.length);
        byte[] encryptedData = new byte[data.length - iv.length];
        System.arraycopy(data, iv.length, encryptedData, 0, encryptedData.length);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new IvParameterSpec(iv));
        return cipher.doFinal(encryptedData);
    }

    public static void encryptFile(String inputFilePath, String outputFilePath, String password) {
        try {
            Key key = deriveKey(password);
            File file = new File(inputFilePath);
            byte[] encryptedData = encrypt(file, key.getEncoded());
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(encryptedData);
            }
            System.out.println("File encrypted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during encryption: " + e.getMessage());
        }
    }

    public static void decryptFile(String inputFilePath, String outputFilePath, String password) {
        try {
            Key key = deriveKey(password);
            byte[] encryptedData = new byte[(int) new File(inputFilePath).length()];
            try (FileInputStream fis = new FileInputStream(inputFilePath)) {
                fis.read(encryptedData);
            }
            byte[] decryptedData = decrypt(encryptedData, key.getEncoded());
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(decryptedData);
            }
            System.out.println("File decrypted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during decryption: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String password = "YourSecretPassword";
        String inputFilePath = "path/to/input/file.txt";
        String encryptedFilePath = "path/to/encrypted/file.enc";
        String decryptedFilePath = "path/to/decrypted/file.txt";

        // Encrypt the file
        encryptFile(inputFilePath, encryptedFilePath, password);

        // Decrypt the file
        decryptFile(encryptedFilePath, decryptedFilePath, password);
    }
}
