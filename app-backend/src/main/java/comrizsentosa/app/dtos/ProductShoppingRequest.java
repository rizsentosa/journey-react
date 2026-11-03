package comrizsentosa.app.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductShoppingRequest(
    @NotBlank String name,
    @NotBlank BigDecimal price,
    @NotBlank Integer stockQuantity,
    @NotBlank String imageUrl) {

}
