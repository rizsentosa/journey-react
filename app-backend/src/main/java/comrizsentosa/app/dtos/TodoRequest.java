package comrizsentosa.app.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TodoRequest(
    @NotBlank String title,
    @NotBlank String description,
    @NotNull LocalDate date) {

}
