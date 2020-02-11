package pl.twino.microloan.service;

import pl.twino.microloan.dto.LoanApplicationDto;
import pl.twino.microloan.exception.LoanDoesNotExistException;

public interface MicroloanService {

    void processApplication(LoanApplicationDto loanApplicationDto);

    void delayLoan(Long loanId) throws LoanDoesNotExistException;
}
