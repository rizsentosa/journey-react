package comrizsentosa.app.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
// import lombok.extern.jackson.Jacksonized;

@Builder
public record PlacesResponse(
    @NotBlank String id,
    @NotBlank String title,
    @NotNull Images image,
    @NotNull BigDecimal lat,
    @NotNull BigDecimal lon) {

  @Builder
  public static record Images(String src, String alt) {

  }
}
