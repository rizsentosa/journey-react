package comrizsentosa.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {
  private static final long MAX_FILE_SIZE = 2 * 1024 * 2024;
  private static final java.util.List<String> ALLOWED_MIME_TYPES_LIST = java.util.Arrays.asList(
      "image/jpeg", "image/png", "image/webp", "image/jpg");

  public void validate(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("file tidak boleh kosong");
    }
    if (file.getSize() > MAX_FILE_SIZE) {
      throw new IllegalArgumentException("ukuran file terlalu besar maksimal 2MB");
    }
    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_MIME_TYPES_LIST.contains(contentType)) {
      throw new IllegalArgumentException("tipe file tidak didukung, hanya format (jpeg,jpg,png,webp) yang didukung");
    }
  }
}
