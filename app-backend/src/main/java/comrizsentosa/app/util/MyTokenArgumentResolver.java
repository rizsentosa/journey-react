package comrizsentosa.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class MyTokenArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(org.springframework.core.MethodParameter parameter) {
    return parameter.hasParameterAnnotation(ValidMyToken.class);
  }

  @Override
  public Object resolveArgument(org.springframework.core.MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    String token = request != null
        ? request.getHeader("My-Token")
        : null;
    if (token == null || !token.equals("qwerty123@")) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missing my-token");
    }
    return token;
  }
}
