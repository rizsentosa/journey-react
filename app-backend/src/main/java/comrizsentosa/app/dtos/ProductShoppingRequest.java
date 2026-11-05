package comrizsentosa.app.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductShoppingRequest(
    @NotBlank String name,
    @NotNull @Positive BigDecimal price,
    @NotNull @Positive Integer stockQuantity,
    @NotBlank String imageUrl) {

}
