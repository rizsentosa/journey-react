package comrizsentosa.app.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;

import lombok.Builder;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record ProductShoppingResponse(
    String id,
    String name,
    Integer stockQuantity,
    String imageUrl,
    Boolean isActive,
    BigDecimal price,
    @JsonInclude(JsonInclude.Include.ALWAYS) List<CartShoppingResponse> cartItems,
    OffsetDateTime createdDate) {

}
