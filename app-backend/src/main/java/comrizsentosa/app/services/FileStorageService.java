package comrizsentosa.app.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import java.util.Optional;
import comrizsentosa.app.dtos.ImageResource;

@Component
public class FileStorageService {
  @Value("${file.upload-dir:images}")
  private String dir;
  private Path fileStorageLocation;

  @PostConstruct
  public void init() {
    this.fileStorageLocation = Paths.get(dir + "/profiles").toAbsolutePath().normalize();
    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception e) {
      throw new RuntimeException("tidak dapat membuat direktori penyimpanan file");
    }
  }

  public String storeFile(MultipartFile file) {
    try {
      String originalName = file.getOriginalFilename();
      String extension = "";
      if (originalName != null && originalName.contains(".")) {
        extension = originalName.substring(originalName.lastIndexOf("."));
      }
      String uniqueName = UUID.randomUUID().toString().replace("-", "") + extension;
      Path targetLocation = this.fileStorageLocation.resolve(uniqueName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return uniqueName;
    } catch (IOException e) {
      throw new RuntimeException("gagal menyimpan file, terjadi kesalahan pada server.", e);
    }
  }

  public Optional<ImageResource> readFile(String filename) {
    Path filePath = this.fileStorageLocation.resolve(filename).normalize();
    // avoid attack Path Traversal
    if (!filePath.startsWith(this.fileStorageLocation) || !Files.exists(filePath) || Files.isDirectory(filePath)) {
      return Optional.empty();
    }
    try {
      String probedType = Files.probeContentType(filePath);
      MediaType mediaType = (probedType != null)
          ? MediaType.parseMediaType(probedType)
          : MediaType.APPLICATION_OCTET_STREAM;

      Resource resource = new UrlResource(filePath.toUri());
      return Optional.of(ImageResource.builder().resource(resource).mediaType(mediaType).build());
    } catch (MalformedInputException e) {
      return Optional.empty();
    } catch (IOException e) {
      throw new UncheckedIOException("Gagal membaca file gambar: " + filename, e);
    }
  }
}
