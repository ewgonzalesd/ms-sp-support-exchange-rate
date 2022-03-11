package pe.com.egonzalesd.msspsupportexchangerate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Setter
@ToString
@Table
@Entity(name = "user")
public class UserModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private String username;
    private String pwd;

    @Column(name = "numdoc")
    private String numDoc;
    @Column(name = "typedoc")
    private String typeDoc;

    private Boolean reading;
    private Boolean write;
}
