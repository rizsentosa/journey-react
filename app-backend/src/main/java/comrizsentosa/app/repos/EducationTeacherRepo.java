package comrizsentosa.app.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comrizsentosa.app.models.school.TeacherEducation;

public interface EducationTeacherRepo extends JpaRepository<TeacherEducation, String> {
  Optional<TeacherEducation> findById(String id);
}
