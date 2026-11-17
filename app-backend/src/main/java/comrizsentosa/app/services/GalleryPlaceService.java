package comrizsentosa.app.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import comrizsentosa.app.dtos.PlacesResponse;
import comrizsentosa.app.dtos.ImageResource;
import comrizsentosa.app.dtos.PlacesRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryPlaceService {

  private static final TypeReference<List<PlacesResponse>> PLACES_TYPE = new TypeReference<>() {
  };
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final ObjectMapper mapper;

  public List<PlacesResponse> places() {
    return readFile("data/places.json");
  }

  public List<PlacesResponse> userPlaces() {
    return readFile("data/user-places.json");
  }

  public void updateSelectedPlaces(PlacesRequest request) {
    lock.writeLock().lock();
    try {
      mapper.writeValue(Files.newOutputStream(Path.of("data/user-places.json")), request.places());
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void deleteUserPlaces(String id) {
    lock.writeLock().lock();
    try {
      List<PlacesResponse> places = readFile("data/user-places.json");
      places.removeIf(p -> p.id().equals(id));
      mapper.writeValue(Files.newOutputStream(
          Path.of("data/user-places.json")),
          places.isEmpty() ? new ArrayList<>() : places);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public Optional<ImageResource> getImage(String filename) {
    Path baseDir = Path.of("images/places_img").toAbsolutePath().normalize();
    Path filePath = baseDir.resolve(filename).normalize();

    // avoid attack Path Traversal
    if (!filePath.startsWith(baseDir) || !Files.exists(filePath) || Files.isDirectory(filePath)) {
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

  private List<PlacesResponse> readFile(String location) {
    lock.readLock().lock();
    Path path = Path.of(location);
    if (!Files.exists(path)) {
      return new ArrayList<>();
    }

    try (InputStream is = Files.newInputStream(path)) {
      return mapper.readValue(is, PLACES_TYPE);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      lock.readLock().unlock();
    }
  }
}
