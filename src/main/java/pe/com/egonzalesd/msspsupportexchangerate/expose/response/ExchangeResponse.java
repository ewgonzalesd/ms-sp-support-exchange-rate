package pe.com.egonzalesd.msspsupportexchangerate.expose.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.egonzalesd.msspsupportexchangerate.utils.enums.EnumCoin;

import java.io.Serializable;
import java.math.BigDecimal;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangeResponse implements Serializable {
    private BigDecimal exchangeRateAmount;
    private BigDecimal amount;
    private EnumCoin from;
    private EnumCoin to;
    private BigDecimal exchangeRate;
    private String date;
}
