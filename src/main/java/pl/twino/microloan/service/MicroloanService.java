package pl.twino.microloan.service;

import pl.twino.microloan.dto.LoanApplicationDto;

public interface MicroloanService {

    void processApplication(LoanApplicationDto loanApplicationDto);

    void delayLoan(Long customerId, Long loanId);
}
