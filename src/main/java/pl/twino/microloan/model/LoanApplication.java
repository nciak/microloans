package pl.twino.microloan.model;

import lombok.Getter;
import lombok.Setter;
import pl.twino.microloan.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = Constants.LOAN_APPLICATION_ENTITY_TABLE_NAME)
@SequenceGenerator(name = Constants.LOAN_APPLICATION_SEQUENCE_NAME, allocationSize = 1)
@Getter
@Setter
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = Constants.LOAN_APPLICATION_SEQUENCE_NAME)
    private Long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "end_date_term")
    private LocalDate endDateTerm;

    @Column(name = "ip_address")
    private String ipAddress;

}
