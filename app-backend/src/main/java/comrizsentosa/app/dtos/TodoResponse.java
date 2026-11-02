package comrizsentosa.app.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record TodoResponse(
    String id,
    String title,
    String description,
    List<TaskResponse> task,
    LocalDate date) {

}
