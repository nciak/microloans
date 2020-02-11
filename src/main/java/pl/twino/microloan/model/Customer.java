package pl.twino.microloan.model;

import lombok.Getter;
import lombok.Setter;
import pl.twino.microloan.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = Constants.CUSTOMER_ENTITY_TABLE_NAME)
@SequenceGenerator(name = Constants.CUSTOMER_SEQUENCE_NAME, allocationSize = 1)
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = Constants.CUSTOMER_SEQUENCE_NAME)
    private Long id;

    @OneToMany(mappedBy = "customer")
    List<LoanGranted> loans;
}
