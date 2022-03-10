package pe.com.egonzalesd.msspsupportexchangerate.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumEvent {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    SELECT("SELECT");
    private final String instruction;
}
