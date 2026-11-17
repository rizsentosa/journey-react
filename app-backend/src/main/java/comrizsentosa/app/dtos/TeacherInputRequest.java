package comrizsentosa.app.dtos;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

// import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;
import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TeacherInputRequest(
    @NotBlank @Size(max = 16) String nuptk,
    @NotBlank @Size(max = 18) String nip,
    @NotBlank @Size(max = 30) String fullname,
    @NotBlank @Size(max = 16) String nik,
    @NotBlank @Size(max = 6) String gender,
    @NotBlank @Size(max = 100) String placeOfBirth,
    @NotNull LocalDate dateBirth,
    @NotBlank @Size(max = 50) String religion,
    @Size(max = 20) String phone,
    @NotBlank @Email String email,
    @NotNull MultipartFile image,
    @NotNull List<@NotNull @Valid TeacherEducation> educations) {

}
