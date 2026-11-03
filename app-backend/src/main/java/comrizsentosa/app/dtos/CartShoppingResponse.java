package comrizsentosa.app.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record CartShoppingResponse(
    String id,
    String name,
    Integer quantity,
    BigDecimal price,
    BigDecimal subTotal,
    OffsetDateTime addedAt) {

}
