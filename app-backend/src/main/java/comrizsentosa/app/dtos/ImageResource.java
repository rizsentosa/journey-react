package comrizsentosa.app.dtos;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import lombok.Builder;

@Builder
public record ImageResource(Resource resource, MediaType mediaType) {

}
