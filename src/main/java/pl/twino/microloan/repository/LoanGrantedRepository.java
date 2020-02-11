package pl.twino.microloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twino.microloan.model.LoanGranted;

@Repository
public interface LoanGrantedRepository extends JpaRepository<LoanGranted, Long> {
}
