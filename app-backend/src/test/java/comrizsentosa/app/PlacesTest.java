package comrizsentosa.app;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import comrizsentosa.app.dtos.PlacesRequest;
import comrizsentosa.app.dtos.PlacesResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlacesTest {

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1000)
  @SneakyThrows
  void testRootLocation() {
    Path baseDir = Path.of("images").toAbsolutePath().normalize();
    log.info("{}", baseDir.toAbsolutePath());
    log.info("{}", Files.exists(baseDir));
    log.info("{}", Files.exists(Paths.get(baseDir.toString(), "places_img")));
  }

  @Test
  @Order(1)
  void test() {
    Path path = Path.of("data/places.json");
    log.info("{}", path.toAbsolutePath());
    log.info("{}", Files.exists(path));
  }

  @Test
  @Order(2)
  void testUpdate() throws Exception {

    List<PlacesResponse> places = List.of(
        PlacesResponse.builder()
            .id("p13")
            .title("Taj Mahal")
            .image(
                PlacesResponse.Images.builder().src("taj-mahal.jpg")
                    .alt("The iconic Taj Mahal reflecting in its surrounding waters during sunrise.").build())
            .lat(BigDecimal.valueOf(27.1751))
            .lon(BigDecimal.valueOf(78.0421))
            .build());

    PlacesRequest request = PlacesRequest.builder().places(places).build();

    mockMvc.perform(
        put("/api/v1/public/places/user-places")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(request)))
        .andExpect(
            status().isOk());
  }

  @Test
  @Order(5)
  void testDelete() throws Exception {
    mockMvc.perform(
        delete("/api/v1/public/places/user-places/p13"))
        .andExpect(
            status().isOk());
  }

  @Test
  @Order(3)
  void testGet1() throws Exception {
    mockMvc.perform(
        get("/api/v1/public/places"))
        .andExpect(
            status().isOk());
  }

  @Test
  @Order(4)
  void testGet2() throws Exception {
    mockMvc.perform(
        get("/api/v1/public/places/user-places"))
        .andExpect(
            status().isOk());
  }
}
