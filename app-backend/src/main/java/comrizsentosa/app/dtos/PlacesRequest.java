package comrizsentosa.app.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PlacesRequest(
    @NotNull List<@Valid @NotNull PlacesResponse> places) {
}
