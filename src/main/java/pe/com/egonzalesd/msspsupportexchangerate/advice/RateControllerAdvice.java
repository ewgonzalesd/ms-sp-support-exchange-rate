package pe.com.egonzalesd.msspsupportexchangerate.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import pe.com.egonzalesd.msspsupportexchangerate.business.exception.BusinessException;
import pe.com.egonzalesd.msspsupportexchangerate.business.exception.ErrorContent;
import pe.com.egonzalesd.msspsupportexchangerate.business.exception.ErrorResponse;

@RestControllerAdvice
public class RateControllerAdvice {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(ErrorContent.builder()
                        .systemMessage(exception.getDescription())
                        .traceId(exception.getId())
                        .code(exception.getCode())
                        .build())
                .build(),
                exception.getHttpStatus());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizedThrowable(Throwable exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .error(ErrorContent.builder()
                        .systemMessage(exception.getMessage())
                        .traceId("")
                        .code("00")
                        .build())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizedException(Exception exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .error(ErrorContent.builder()
                        .systemMessage(exception.getMessage())
                        .traceId("")
                        .code("00")
                        .build())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizedException(AuthenticationException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .error(ErrorContent.builder()
                        .systemMessage(exception.getMessage())
                        .traceId("")
                        .code("00")
                        .build())
                .build(),
                HttpStatus.BAD_REQUEST);
    }
}
