package comrizsentosa.app.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import comrizsentosa.app.models.task.Tasks;

public interface TaskRepo extends JpaRepository<Tasks, UUID> {
  Optional<Tasks> findById(UUID id);

  @Query("SELECT task FROM Tasks task")
  @EntityGraph(attributePaths = { "todo" })
  List<Tasks> getTasksWithRelationTodo();

  @Query("SELECT COUNT(task) FROM Tasks task WHERE task.todo.id = :id")
  Long countTasksByTodoId(@Param("id") UUID todoId);
}
