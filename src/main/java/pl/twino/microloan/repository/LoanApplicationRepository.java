package pl.twino.microloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.twino.microloan.model.LoanApplication;


@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    @Query("select count(l) from LoanApplication l where l.ipAddress = ?1")
    int countByIpAddress(String ipAddress);

}
