package comrizsentosa.app.services;

import java.util.List;
import java.util.UUID;

import comrizsentosa.app.exception.BadRequestException;
import comrizsentosa.app.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comrizsentosa.app.dtos.TaskRequest;
import comrizsentosa.app.dtos.TaskResponse;
import comrizsentosa.app.models.Tasks;
import comrizsentosa.app.models.Todos;
import comrizsentosa.app.repos.TaskRepo;
import comrizsentosa.app.repos.TodoRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepo taskRepo;
  private final TodoRepo todoRepo;

  @Transactional
  public TaskResponse taskSave(TaskRequest request) {
    Tasks entityTask = Tasks.builder().title(request.title()).build();
    Todos todo = todoRepo.findById(UUID.fromString(request.todoId())).get();

    entityTask.setTodo(todo);
    entityTask = taskRepo.save(entityTask);

    return new TaskResponse(entityTask.getId().toString(), entityTask.getTitle());
  }

  public List<TaskResponse> taskAll() {
    List<TaskResponse> tasksResponse = taskRepo.findAll().stream()
        .map(task -> new TaskResponse(task.getId().toString(), task.getTitle()))
        .toList();
    return tasksResponse;
  }

  public void deleteTask(String id) {
    Tasks taskEntity = findTaskById(id);
    Todos todoEntity = findTodoById(taskEntity.getTodo().getId());
    todoEntity.getTasks().remove(taskEntity);
    taskRepo.delete(taskEntity);
  }

  public TaskResponse updateTask(String id, TaskRequest request) {
    Tasks taskEntity = findTaskById(id);
    Todos todoEntity = findTodoById(UUID.fromString(request.todoId()));

    taskEntity.setTitle(request.title());
    taskEntity.setTodo(todoEntity);

    taskEntity = taskRepo.save(taskEntity);
    TaskResponse response = new TaskResponse(taskEntity.getId().toString(), taskEntity.getTitle());
    return response;
  }

  private Tasks findTaskById(String id) {
    var uuid_id = parseUuid(id);
    Tasks taskEntity = taskRepo.findById(uuid_id)
        .orElseThrow(() -> new ResourceNotFoundException("data with id " + id + " not found"));
    return taskEntity;
  }

  private Todos findTodoById(UUID id) {
    Todos todoEntity = todoRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("todo with id " + id + " not found"));
    return todoEntity;
  }

  private UUID parseUuid(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("invalid uuid format " + id);
    }
  }
}
