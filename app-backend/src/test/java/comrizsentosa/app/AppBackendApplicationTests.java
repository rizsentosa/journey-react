package comrizsentosa.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import comrizsentosa.app.dtos.TaskRequest;
import comrizsentosa.app.dtos.TodoRequest;
import comrizsentosa.app.models.task.Tasks;
import comrizsentosa.app.models.task.Todos;
import comrizsentosa.app.repos.TaskRepo;
import comrizsentosa.app.repos.TodoRepo;
import comrizsentosa.app.services.TodoService;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class AppBackendApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TodoService service;

  @Autowired
  private TodoRepo todoRepo;

  @Autowired
  private TaskRepo taskRepo;

  @Autowired
  private ObjectMapper mapper;

  @Test
  void testDeleteTask() throws Exception {

    Tasks task = taskRepo.findAll().stream().findFirst().get();
    String taskId = task.getId().toString();
    String todoId = task.getTodo().getId().toString();

    // 400 bad request
    mockMvc.perform(
        delete("/api/todos/task/wrong-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // 404 not found
    mockMvc.perform(
        delete("/api/todos/task/b831cc52-6f38-414a-bbfa-219f54f365b7")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    // 200 ok
    mockMvc.perform(
        delete("/api/todos/task/" + taskId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // 200 display data todo parent relation
    String json = mockMvc.perform(
        get("/api/todos/" + todoId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

  @Test
  void testUpdateTasks() throws Exception {
    // id task -> a690ef7a-3df1-4e95-9c55-104daf63b7e5
    // id todo -> b831cc52-6f38-414a-bbfa-219f54f365b7

    String todoId = "b831cc52-6f38-414a-bbfa-219f54f365b7";
    TaskRequest content = new TaskRequest(todoId, "test task recent updated");

    // 404 not found
    mockMvc.perform(
        put("/api/todos/task/b831cc52-6f38-414a-bbfa-219f54f365b7")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(content))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    // 400 bad request
    mockMvc.perform(
        put("/api/todos/task/a690ef7a-3df1-4e95-9c55-104daf63b7e5")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(new TaskRequest("", "")))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // 200 success
    String json = mockMvc.perform(
        put("/api/todos/task/a690ef7a-3df1-4e95-9c55-104daf63b7e5")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(content))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

  @Test
  void testFindAllTasks() throws Exception {
    String json = mockMvc.perform(
        get("/api/todos/task")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            status().isOk())
        .andReturn().getResponse().getContentAsString();

    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

  @Test
  void testTaskInsertData() throws Exception {

    Todos todo = todoRepo.findAll().stream().findFirst().get(); // id - b831cc52-6f38-414a-bbfa-219f54f365b7
    String todoId = todo.getId().toString();

    TaskRequest content = new TaskRequest(todoId, "test task 1");
    String json = mockMvc.perform(
        post("/api/todos/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(content))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    Object jsonObject = mapper.readValue(json, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

  @Test
  void testSideBar() throws Exception {
    mockMvc.perform(
        get("/api/todos/side-bar")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            status().isOk());
  }

  @Test
  void testDelete() throws Exception {
    // d86d84da-4b69-4fea-b52f-b517ad8694df
  }

  @Test
  void testPost() throws Exception {

    // simulasi gagal 401
    TodoRequest request = TodoRequest.builder().build();
    String json = mapper.writeValueAsString(request);

    mockMvc.perform(
        post("/api/todos")
            .contentType("application/json")
            .content(json)
            .accept("application/json"))
        .andExpect(status().isBadRequest());

    // simulasi berhasil 200
    request = TodoRequest.builder().title("aktifitas sehari-hari di rumah")
        .description("Amet laboris nisi adipisicing commodo ea fugiat veniam eiusmod incididunt consequat excepteur.")
        .date(LocalDate.now()).build();
    json = mapper.writeValueAsString(request);

    String jsonSuccess = mockMvc.perform(
        post("/api/todos")
            .contentType("application/json")
            .content(json)
            .accept("application/json"))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    Object jsonObject = mapper.readValue(jsonSuccess, Object.class);
    String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    log.info("\n{}", prettyJson);
  }

  @Test
  void testPaginate() throws Exception {
    String json = mockMvc.perform(
        get("/api/todos")
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

  @Test
  void testBean() {
    Assertions.assertNotNull(service);
  }

  @Test
  void testRepo() {
    todoRepo.findAll().forEach(d -> log.info("{}", d.getTitle()));
  }
}
