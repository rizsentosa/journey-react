package comrizsentosa.app.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comrizsentosa.app.dtos.TeacherInputRequest;
import comrizsentosa.app.services.FileStorageService;
import comrizsentosa.app.services.TeacherService;
import comrizsentosa.app.validation.FileValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/public/teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {
  private final FileStorageService fileStorageService;
  private final FileValidator fileValidator;
  private final TeacherService teacherService;

  @PostMapping
  public ResponseEntity<String> createTeacher(
      @ModelAttribute @Valid TeacherInputRequest request) {
    fileValidator.validate(request.image());
    teacherService.create(request);
    log.info("log teacher controller {}", request);
    return ResponseEntity.ok("berhasil melakukan simpan data teacher");
  }

  @GetMapping("/profile/{image}")
  public ResponseEntity<Resource> renderImage(@PathVariable String image) {
    return fileStorageService.readFile(image)
        .map(file -> ResponseEntity.ok().contentType(file.mediaType()).body(file.resource()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
