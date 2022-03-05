package pe.com.egonzalesd.msspsupportexchangerate.business.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorContent {
  private String systemMessage;
  private String traceId;
  private String code;
}
