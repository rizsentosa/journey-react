package comrizsentosa.app.dtos;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CartShoppingRequest(
    @NotBlank String productId,
    @NotBlank @Min(value = 1) Integer quantity,
    @NotBlank OffsetDateTime addedAt) {

}
