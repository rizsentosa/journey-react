package comrizsentosa.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comrizsentosa.app.dtos.TaskResponse;
import comrizsentosa.app.dtos.TodoRequest;
import comrizsentosa.app.dtos.TodoResponse;
import comrizsentosa.app.dtos.TodoSideBarResponse;
import comrizsentosa.app.exception.BadRequestException;
import comrizsentosa.app.exception.ResourceNotFoundException;
import comrizsentosa.app.models.Tasks;
import comrizsentosa.app.models.Todos;
import comrizsentosa.app.repos.TodoRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepo todoRepo;

  public TodoResponse findOneTodo(String id) {
    UUID uuid_id = parsedUUID(id);
    Todos todoEntity = todoRepo.findById(uuid_id)
        .orElseThrow(() -> new ResourceNotFoundException("data not found witn id " + id));

    var taskEntity = todoEntity.getTasks()
        .stream()
        .map(task -> new TaskResponse(task.getId().toString(), task.getTitle()))
        .toList();

    var response = new TodoResponse(todoEntity.getId().toString(), todoEntity.getTitle(), todoEntity.getDescription(),
        taskEntity, todoEntity.getDate());

    return response;
  }

  public List<TodoSideBarResponse> todoSideBar() {
    List<TodoSideBarResponse> response = todoRepo.findAll()
        .stream()
        .map(t -> new TodoSideBarResponse(t.getId().toString(), t.getTitle()))
        .toList();
    return response;
  }

  public Page<TodoResponse> getTodoWithTasks(Pageable pageable) {
    Page<Todos> allTodos = todoRepo.gettingTodoWithTasks(pageable);
    List<TodoResponse> todos = allTodos.getContent().stream()
        .map(t -> {
          var tasks = t.getTasks();
          if (tasks.isEmpty()) {
            return TodoResponse.builder()
                .id(t.getId().toString())
                .title(t.getTitle())
                .description(t.getDescription())
                .date(t.getDate())
                .task(new ArrayList<>())
                .build();
          }

          List<TaskResponse> taskMapped = tasks.stream()
              .map(task -> {
                return new TaskResponse(
                    task.getId().toString(), task.getTitle());
              }).toList();

          var todo = TodoResponse.builder()
              .id(t.getId().toString())
              .title(t.getTitle())
              .description(t.getDescription())
              .date(t.getDate())
              .task(taskMapped)
              .build();

          return todo;
        })
        .toList();

    // if (todos.isEmpty()) {
    // return Page.empty();
    // }
    return new PageImpl<>(todos, pageable, allTodos.getTotalElements());
  }

  @Transactional
  public TodoResponse savingTodo(TodoRequest request) {
    Todos entity = Todos.builder()
        .title(request.title())
        .description(request.description())
        .date(request.date())
        .build();
    Todos todo = todoRepo.save(entity);
    return TodoResponse.builder().title(todo.getTitle()).description(todo.getDescription()).id(todo.getId().toString())
        .date(todo.getDate()).build();
  }

  @Transactional
  public String deletedTodo(String id) {
    Optional<Todos> existed = todoRepo.findById(UUID.fromString(id));
    if (existed.isEmpty()) {
      return "data with id " + id + " not found";
    }
    todoRepo.delete(existed.get());
    return "has been deleted";
  }

  private UUID parsedUUID(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException(e.getMessage() + " " + id);
    }
  }
}
