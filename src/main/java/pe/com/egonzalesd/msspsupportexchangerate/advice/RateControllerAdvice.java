package pe.com.egonzalesd.msspsupportexchangerate.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
}
