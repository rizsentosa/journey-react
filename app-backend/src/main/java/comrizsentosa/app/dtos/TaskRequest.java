package comrizsentosa.app.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(@NotBlank String todoId, @NotBlank String title) {

}
