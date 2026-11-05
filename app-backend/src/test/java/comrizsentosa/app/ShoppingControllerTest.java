package comrizsentosa.app;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import comrizsentosa.app.dtos.CartShoppingRequest;
import comrizsentosa.app.dtos.ProductShoppingRequest;
import comrizsentosa.app.dtos.ProductShoppingResponse;
import comrizsentosa.app.repos.ProductShoppingRepo;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ShoppingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private ProductShoppingRepo productRepo;

  @Test
  void addingProductToCart() throws Exception {
    ProductShoppingRequest requestProduct = ProductShoppingRequest.builder()
        .name("Product 1")
        .price(BigDecimal.valueOf(100))
        .stockQuantity(10)
        .imageUrl("https://via.placeholder.com/150")
        .build();

    String jsonProduct = mockMvc.perform(
        post("/api/public/shop/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(requestProduct)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    ProductShoppingResponse product = mapper.readValue(jsonProduct, ProductShoppingResponse.class);

    CartShoppingRequest request = CartShoppingRequest.builder()
        .productId(product.id())
        .quantity(4)
        .addedAt(java.time.OffsetDateTime.now())
        .build();

    String json = mockMvc.perform(
        post("/api/public/shop/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    log.info("\n{}", prettyJson);

    mockMvc.perform(
        delete("/api/public/shop/products/" + product.id())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void productDelete() throws Exception {
    ProductShoppingRequest request = ProductShoppingRequest.builder()
        .name("Product 2")
        .price(BigDecimal.valueOf(100))
        .stockQuantity(10)
        .imageUrl("https://via.placeholder.com/150")
        .build();

    String json = mockMvc.perform(
        post("/api/public/shop/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    ProductShoppingResponse response = mapper.readValue(json, ProductShoppingResponse.class);

    String id = response.id();
    String notFoundId = "00000000-0000-0000-0000-000000000000";

    // 400 bad request
    mockMvc.perform(
        delete("/api/public/shop/products/wrong-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // 404 not found
    mockMvc.perform(
        delete("/api/public/shop/products/" + notFoundId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    // 200 ok
    mockMvc.perform(
        delete("/api/public/shop/products/" + id)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void productUpdate() throws Exception {
    ProductShoppingRequest requestPost = ProductShoppingRequest.builder()
        .name("Product 2")
        .price(BigDecimal.valueOf(100))
        .stockQuantity(10)
        .imageUrl("https://via.placeholder.com/150")
        .build();

    // 201 created Post
    String jsonPost = mockMvc.perform(
        post("/api/public/shop/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(requestPost)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    ProductShoppingResponse response = mapper.readValue(jsonPost, ProductShoppingResponse.class);

    String id = response.id();
    String notFoundId = "00000000-0000-0000-0000-000000000000";
    ProductShoppingRequest request = ProductShoppingRequest.builder()
        .name("Product 2 updated")
        .price(BigDecimal.valueOf(100))
        .stockQuantity(10)
        .imageUrl("https://via.placeholder.com/150")
        .build();

    // 400 bad request updated
    mockMvc.perform(
        put("/api/public/shop/products/wrong-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // 404 not found updated
    mockMvc.perform(
        put("/api/public/shop/products/" + notFoundId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());

    // 200 ok updated
    String json = mockMvc.perform(
        put("/api/public/shop/products/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    log.info("\n{}", prettyJson);

    // 200 Ok deleted
    mockMvc.perform(
        delete("/api/public/shop/products/" + id)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void productSave() throws Exception {
    // 400 bad request save
    mockMvc.perform(
        post("/api/public/shop/products")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // 201 Created save
    ProductShoppingRequest request = ProductShoppingRequest.builder()
        .name("Product 1")
        .price(BigDecimal.valueOf(100))
        .stockQuantity(10)
        .imageUrl("https://via.placeholder.com/150")
        .build();

    String json = mockMvc.perform(
        post("/api/public/shop/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    ProductShoppingResponse jsonObject = mapper.readValue(json, ProductShoppingResponse.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    log.info("\n{}", prettyJson);

    // 200 Ok deleted
    mockMvc.perform(
        delete("/api/public/shop/products/" + jsonObject.id())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void productFindOne() throws Exception {
    // 400 bad request
    mockMvc.perform(
        get("/api/public/shop/products/wrong-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // 404 not found
    mockMvc.perform(
        get("/api/public/shop/products/b831cc52-6f38-414a-bbfa-219f54f365b7")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    // 200 Ok
    String id = productRepo.findAll().get(0).getId().toString();
    String json = mockMvc.perform(
        get("/api/public/shop/products/" + id)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

  @Test
  void productPaginate() throws Exception {
    String json = mockMvc.perform(
        get("/api/public/shop/products")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "id")
            .param("direction", "desc"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

}
