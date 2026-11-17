package comrizsentosa.app.models.school;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import comrizsentosa.app.util.UlidGenerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "education")
@SQLRestriction(value = "deleted_at is null")
@Builder
@Getter
@Setter
public class TeacherEducation {
  @Id
  @UlidGenerated
  private String id;

  @Column(name = "name", nullable = false, length = 100)
  private String schoolName;

  @Column(nullable = false, length = 4)
  private String degree;

  @Column(nullable = false, length = 100)
  private String major;

  @Column(name = "start_date_educate", nullable = false)
  private LocalDate startDateEducation;

  @Column(name = "end_date_educate", nullable = false)
  private LocalDate endDateEducation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_teacher", nullable = false, referencedColumnName = "id")
  private Teacher teacherEducation;
}
