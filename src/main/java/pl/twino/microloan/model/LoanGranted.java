package pl.twino.microloan.model;

import lombok.Getter;
import lombok.Setter;
import pl.twino.microloan.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = Constants.LOAN_GRANTED_ENTITY_TABLE_NAME)
@SequenceGenerator(name = Constants.LOAN_GRANTED_SEQUENCE_NAME, allocationSize = 1)
@Getter
@Setter
public class LoanGranted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = Constants.LOAN_GRANTED_SEQUENCE_NAME)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    private LoanApplication loanApplication;

    @Column(name = "delayed")
    private boolean delayed;

}
