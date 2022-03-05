package pe.com.egonzalesd.msspsupportexchangerate.expose.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.egonzalesd.msspsupportexchangerate.utils.enums.EnumCoin;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest {
    private BigDecimal amount;
    private EnumCoin from;
    private EnumCoin to;
}
