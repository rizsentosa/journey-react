package comrizsentosa.app.dtos;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CartShoppingRequest(
    @NotBlank String productId,
    @NotNull @Min(value = 1) Integer quantity,
    @NotNull OffsetDateTime addedAt) {

}
