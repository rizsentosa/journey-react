package comrizsentosa.app.models.school;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import comrizsentosa.app.util.UlidGenerated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teacher")
@SQLRestriction(value = "deleted_at is null")
@Builder
@Getter
@Setter
public class Teacher {

  @Id
  @UlidGenerated
  private String id;

  @Column(unique = true, nullable = true, length = 16)
  private String nuptk;

  @Column(unique = true, nullable = true, length = 18)
  private String nip;

  @Column(unique = true, nullable = false, length = 16)
  private String nik;

  @Column(name = "full_name", nullable = false, length = 150)
  private String fullName;

  @Column(nullable = false)
  private String gender;

  @Column(name = "place_of_birth", nullable = false, length = 100)
  private String placeOfBirth;

  @Column(name = "date_birth", nullable = false)
  private LocalDate dateBirth;

  @Column(nullable = false, length = 50)
  private String religion;

  @Column(nullable = true, length = 20)
  private String phone;

  @Column(unique = true, nullable = false, length = 100)
  private String email;

  @Column(nullable = true, length = 150)
  private String image;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "teacherEducation", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
  private Set<TeacherEducation> educations;
}
