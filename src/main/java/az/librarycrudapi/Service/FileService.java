package az.librarycrudapi.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final Path fileStorageLocation;
    private final List<String> allowedMimeTypes = List.of("image/jpeg", "image/png", "application/pdf");
    private final long maxFileSize = 5 * 1024 * 1024;

    public FileService() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Fayl bosdur");
        }

        if (file.getSize() > maxFileSize) {
            throw new RuntimeException("Fayl olcusu maksimum 5MB ola biler");
        }

        try (InputStream inputStream = file.getInputStream()) {
            String detectedType = URLConnection.guessContentTypeFromStream(inputStream);
            if (detectedType == null) {
                detectedType = file.getContentType();
            }

            if (detectedType == null || !allowedMimeTypes.contains(detectedType)) {
                throw new RuntimeException("Yalniz JPEG, PNG ve PDF fayllarina icaze verilir");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Fayl tapilmadi");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
