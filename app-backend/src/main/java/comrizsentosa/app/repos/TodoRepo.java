package comrizsentosa.app.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import comrizsentosa.app.models.Todos;

public interface TodoRepo extends JpaRepository<Todos, UUID> {
  @EntityGraph(attributePaths = { "tasks" })
  Optional<Todos> findById(UUID id);

  @Query("SELECT DISTINCT todo FROM Todos todo LEFT JOIN FETCH todo.tasks")
  List<Todos> findAllWithTasks();

  @EntityGraph(attributePaths = { "tasks" })
  @Query("SELECT todo FROM Todos todo")
  Page<Todos> gettingTodoWithTasks(Pageable pageable);

  @Query("SELECT todo FROM Todos todo JOIN FETCH todo.tasks WHERE todo.id = :id")
  List<Todos> findTodoWithTaskById(@Param("id") UUID id);
}
