package comrizsentosa.app.dtos;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TeacherEducation(
    @NotBlank String schoolName,
    @NotBlank String degree,
    @NotBlank String major,
    @NotNull LocalDate startDateEducation,
    @NotNull LocalDate endDateEducation) {

}
