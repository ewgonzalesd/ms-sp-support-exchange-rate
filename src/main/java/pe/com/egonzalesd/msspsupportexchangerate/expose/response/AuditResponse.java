package pe.com.egonzalesd.msspsupportexchangerate.expose.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditResponse {
    private String typeDoc;
    private String numDoc;
    private String username;
}
