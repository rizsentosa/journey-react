package comrizsentosa.app.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comrizsentosa.app.dtos.TeacherInputRequest;
import comrizsentosa.app.models.school.Teacher;
import comrizsentosa.app.models.school.TeacherEducation;
import comrizsentosa.app.repos.TeacherRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {
  private final TeacherRepo teacherRepo;
  private final FileStorageService fileStorage;

  @Transactional
  public void create(TeacherInputRequest request) {
    String image = fileStorage.storeFile(request.image());

    Teacher entity = toEntity(request);
    entity.setImage(image);

    if (request.educations() != null && !request.educations().isEmpty()) {
      Set<TeacherEducation> educations = request.educations().stream()
          .map(edu -> {
            TeacherEducation education = toEntityEducation(edu);
            education.setTeacherEducation(entity);
            return education;
          })
          .collect(Collectors.toSet());
      entity.setEducations(educations);
    }

    teacherRepo.save(entity);
  }

  private TeacherEducation toEntityEducation(comrizsentosa.app.dtos.TeacherEducation input) {
    TeacherEducation entity = TeacherEducation.builder()
        .schoolName(input.schoolName())
        .degree(input.degree().toLowerCase())
        .major(input.major().toLowerCase())
        .startDateEducation(input.startDateEducation())
        .endDateEducation(input.endDateEducation())
        .build();
    return entity;
  }

  private Teacher toEntity(TeacherInputRequest input) {
    Teacher entity = Teacher.builder()
        .fullName(input.fullname())
        .nuptk(input.nuptk())
        .nip(input.nip())
        .gender(input.gender())
        .nik(input.nik())
        .placeOfBirth(input.placeOfBirth())
        .dateBirth(input.dateBirth())
        .religion(input.religion())
        .phone(input.phone())
        .email(input.email())
        .build();
    return entity;
  }
}
