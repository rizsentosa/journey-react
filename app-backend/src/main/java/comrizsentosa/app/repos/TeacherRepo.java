package comrizsentosa.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import comrizsentosa.app.models.school.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, String> {

  Optional<Teacher> findById(String id);

  List<Teacher> findAll();

}
