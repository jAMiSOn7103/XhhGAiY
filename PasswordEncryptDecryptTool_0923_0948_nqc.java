// 代码生成时间: 2025-09-23 09:48:11
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import java.io.Reader;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
# NOTE: 重要实现细节
import java.util.Base64;

public class PasswordEncryptDecryptTool {
    
    private static final String ALGORITHM = "AES";
# 添加错误处理
    private static final String TRANSFORMATION = "AES";
    private static final String MY_KEY = "MySecretKey";
# 扩展功能模块
    
    // 加密方法
# NOTE: 重要实现细节
    public static String encrypt(String dataToEncrypt) throws Exception {
        byte[] key = MY_KEY.getBytes("UTF-8");
# 改进用户体验
        byte[] iv = new byte[16];
# NOTE: 重要实现细节
        SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    // 解密方法
    public static String decrypt(String encryptedData) throws Exception {
        byte[] key = MY_KEY.getBytes("UTF-8");
        byte[] iv = new byte[16];
        SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }
    
    // 主方法，用于测试加密和解密功能
    public static void main(String[] args) {
        try {
            String originalPassword = "password123";
            String encryptedPassword = encrypt(originalPassword);
            System.out.println("Encrypted Password: " + encryptedPassword);
            
            String decryptedPassword = decrypt(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
