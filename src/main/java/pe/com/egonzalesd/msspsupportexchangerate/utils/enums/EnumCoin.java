package pe.com.egonzalesd.msspsupportexchangerate.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCoin {
    PEN("Nuevo Sol"),
    USD("Dólar estaudinense"),
    EUR("Euro"),
    JPY("Yen japonés");
    private final String coin;
}
