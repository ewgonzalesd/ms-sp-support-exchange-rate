package pe.com.egonzalesd.msspsupportexchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
@Table
@Entity(name = "trace")
public class TraceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String entity;
    private String event;
    @Column(name = "event_user")
    private String eventUser;
    @Column(name = "event_date")
    private Date eventDate;


}
