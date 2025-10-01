// 代码生成时间: 2025-10-02 03:40:22
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.VFS;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// FileCompressor class provides functionality to unzip files using Java I/O and Zip libraries.
public class FileCompressor {

    // Unzips the specified zip file into a specified directory.
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        // Check if the zip file exists.
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            throw new FileNotFoundException("The specified file does not exist: " + zipFilePath);
        }

        // Create the destination directory if it does not exist.
        File destinationDir = new File(destDirectory);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        // Open the zip file and read its contents.
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            // Loop through the zip entries.
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it.
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, create it.
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    // Extracts a file from the zip input stream to the specified location.
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    // Main method for testing the FileCompressor class.
    public static void main(String[] args) {
        FileCompressor compressor = new FileCompressor();
        try {
            // Replace with the path to your zip file and destination directory.
            String zipFilePath = "path/to/your/file.zip";
            String destDirectory = "path/to/destination/directory";
            compressor.unzip(zipFilePath, destDirectory);
            System.out.println("Unzipped successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
