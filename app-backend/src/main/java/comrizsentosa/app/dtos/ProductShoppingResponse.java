package comrizsentosa.app.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductShoppingResponse(
    String id,
    String name,
    Integer stockQuantity,
    String imageUrl,
    Boolean isActive,
    BigDecimal price,
    OffsetDateTime createdDate) {

}
