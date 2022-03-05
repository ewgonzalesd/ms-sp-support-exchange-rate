package pe.com.egonzalesd.msspsupportexchangerate.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class BusinessException extends Exception {
    private HttpStatus httpStatus;
    private String id;
    private String code;
    private String description;
}
