package comrizsentosa.app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comrizsentosa.app.dtos.TaskRequest;
import comrizsentosa.app.dtos.TaskResponse;
import comrizsentosa.app.dtos.TodoRequest;
import comrizsentosa.app.dtos.TodoResponse;
import comrizsentosa.app.dtos.TodoSideBarResponse;
import comrizsentosa.app.services.TaskService;
import comrizsentosa.app.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodosController {

  private final TodoService todoService;
  private final TaskService taskService;

  @GetMapping
  public ResponseEntity<Page<TodoResponse>> getTodoWithTasks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sort", defaultValue = "id") String sort,
      @RequestParam(value = "direction", defaultValue = "asc") String direction) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
    Page<TodoResponse> response = todoService.getTodoWithTasks(pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/side-bar")
  public ResponseEntity<List<TodoSideBarResponse>> getTodoSideBar() {
    List<TodoSideBarResponse> response = todoService.todoSideBar();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TodoResponse> getOneTodo(@PathVariable String id) {
    TodoResponse response = todoService.findOneTodo(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<TodoResponse> postTodo(@RequestBody @Valid TodoRequest request) {
    TodoResponse response = todoService.savingTodo(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  // controller for task

  @PostMapping("/task")
  public ResponseEntity<TaskResponse> postTask(@RequestBody @Valid TaskRequest request) {
    TaskResponse response = taskService.taskSave(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/task")
  public ResponseEntity<List<TaskResponse>> getTask() {
    List<TaskResponse> response = taskService.taskAll();
    return ResponseEntity.ok(response);
  }

  @PutMapping("/task/{id}")
  public ResponseEntity<TaskResponse> updateTask(@RequestBody @Valid TaskRequest request, @PathVariable String id) {
    TaskResponse response = taskService.updateTask(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/task/{id}")
  public ResponseEntity<HttpStatusCode> deleteTask(@PathVariable String id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
}
