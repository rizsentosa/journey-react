package comrizsentosa.app.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import comrizsentosa.app.util.MyTokenArgumentResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CorstConfig implements WebMvcConfigurer {
  private final MyTokenArgumentResolver myTokenResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(myTokenResolver);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/v1/public/places/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("GET", "PUT", "DELETE")
        .allowedHeaders("Content-Type", "My-Token")
        .allowCredentials(false);

    registry.addMapping("/api/v1/public/teacher/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("GET", "PUT", "DELETE", "POST")
        .allowedHeaders("Content-Type", "My-Token")
        .allowCredentials(false);
  }
}
