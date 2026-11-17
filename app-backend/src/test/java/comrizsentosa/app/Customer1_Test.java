package comrizsentosa.app;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import comrizsentosa.app.models.shopping.Customer;
import comrizsentosa.app.repos.CustomerRepo1;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Slf4j
public class Customer1_Test {
  @Autowired
  private CustomerRepo1 c1;

  @Autowired
  private ObjectMapper mapper;

  @Test
  void testInsert() {
    c1.save(Customer.builder().name("test").build());
  }

  @Test
  void testSee() {
    List<Customer> data = c1.findAll();
    String json = mapper.writeValueAsString(data);

    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }
}
