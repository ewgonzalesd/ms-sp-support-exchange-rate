package pe.com.egonzalesd.msspsupportexchangerate.expose.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.egonzalesd.msspsupportexchangerate.utils.enums.EnumCoin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateResponse implements Serializable {
    private String from;
    private String date;
    private Map<String,BigDecimal> rates;
}
