package comrizsentosa.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comrizsentosa.app.dtos.PlacesRequest;
import comrizsentosa.app.dtos.PlacesResponse;
import comrizsentosa.app.services.GalleryPlaceService;
import comrizsentosa.app.util.ValidMyToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/places")
@RequiredArgsConstructor
public class PlacesController {
  private final GalleryPlaceService gallery;

  @GetMapping
  public ResponseEntity<List<PlacesResponse>> places(@ValidMyToken String token) {
    List<PlacesResponse> response = gallery.places();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{image}")
  public ResponseEntity<Resource> getImage(@PathVariable String image) {
    return gallery.getImage(image)
        .map(file -> ResponseEntity.ok().contentType(file.mediaType()).body(file.resource()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/user-places")
  public ResponseEntity<List<PlacesResponse>> userPlaces(@ValidMyToken String token) {
    List<PlacesResponse> response = gallery.userPlaces();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/user-places")
  public ResponseEntity<Map<String, String>> updateUserPlaces(@RequestBody @Valid PlacesRequest request,
      @ValidMyToken String token) {
    gallery.updateSelectedPlaces(request);
    var response = Map.of("message", "user places updated!");
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/user-places/{id}")
  public ResponseEntity<Map<String, Object>> deletedPlaces(@PathVariable String id, @ValidMyToken String token) {
    gallery.deleteUserPlaces(id);
    Map<String, Object> response = Map.of("message", "Place deleted successfully");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

}
