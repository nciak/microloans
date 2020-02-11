package pl.twino.microloan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.twino.microloan.Constants;
import pl.twino.microloan.dto.LoanApplicationDto;
import pl.twino.microloan.exception.LoanDoesNotExistException;
import pl.twino.microloan.model.Customer;
import pl.twino.microloan.model.LoanApplication;
import pl.twino.microloan.model.LoanGranted;
import pl.twino.microloan.repository.CustomerRepository;
import pl.twino.microloan.repository.LoanApplicationRepository;
import pl.twino.microloan.repository.LoanGrantedRepository;
import pl.twino.microloan.service.MicroloanService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MicroloanServiceImpl implements MicroloanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanGrantedRepository loanGrantedRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public MicroloanServiceImpl(LoanApplicationRepository loanApplicationRepository, LoanGrantedRepository loanGrantedRepository, CustomerRepository customerRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanGrantedRepository = loanGrantedRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void processApplication(LoanApplicationDto loanApplicationDto) {
        LoanApplication loanApplication = createApplicationToSaveInDb(loanApplicationDto);
        loanApplicationRepository.save(loanApplication);

        if (isRiskyApplication(loanApplicationDto)) {
            log.info("The risk level is too high to accept the application.");
        } else {
            LoanGranted loanGranted = convertToLoanGranted(loanApplication);
            loanGrantedRepository.save(loanGranted);

            if (loanApplicationDto.getCustomerId() == null) {
                customerRepository.save(createNewCustomer(loanGranted));
            } else {
                Customer customer = customerRepository.getOne(loanApplicationDto.getCustomerId());
                List<LoanGranted> loanGrantedList = customer.getLoans();
                loanGrantedList.add(loanGranted);
                customerRepository.save(customer);
            }

            log.info("The application has been accepted.");
        }
    }

    private Customer createNewCustomer(LoanGranted loanGranted) {
        Customer customer = new Customer();
        List<LoanGranted> loanGrantedList = new ArrayList<>();
        loanGrantedList.add(loanGranted);
        customer.setLoans(loanGrantedList);

        return customer;
    }

    @Override
    public void delayLoan(Long loanId) throws LoanDoesNotExistException {
        LoanGranted loanToDelay = loanGrantedRepository.findById(loanId)
                .orElseThrow(() -> new LoanDoesNotExistException("Loan " + loanId.toString() + " does not exist!"));

        if (!loanToDelay.isDelayed()) {
            loanToDelay.setDelayed(true);
            LoanApplication loanApplication = loanToDelay.getLoanApplication();
            loanApplication.setEndDateTerm(loanApplication.getEndDateTerm().plusDays(Constants.LOAN_DELAY_DAYS));
            loanApplicationRepository.save(loanApplication);
            loanGrantedRepository.save(loanToDelay);
            log.info("Loan ", loanId, " delayed to ", loanApplication.getEndDateTerm());
        } else {
            log.error("Loan ", loanId, " has already been delayed!");
        }

    }

    private LoanGranted convertToLoanGranted(LoanApplication loanApplication) {
        LoanGranted loanGranted = new LoanGranted();
        loanGranted.setLoanApplication(loanApplication);
        return loanGranted;
    }

    private LoanApplication createApplicationToSaveInDb(LoanApplicationDto loanApplicationDto) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setAmount(loanApplicationDto.getAmount());
        loanApplication.setIpAddress(loanApplicationDto.getIpAddress());
        loanApplication.setEndDateTerm(LocalDate.now().plusDays(loanApplicationDto.getDays()));
        return loanApplication;
    }

    private boolean isRiskyApplication(LoanApplicationDto loanApplicationDto) {
        LocalTime localTime = loanApplicationDto.getDate().toLocalTime();
        if (localTime.isAfter(Constants.RISK_HOURS_START) && localTime.isBefore(Constants.RISK_HOURS_STOP)
                && loanApplicationDto.getAmount() == Constants.MAX_LOAN_AMOUNT) {
            return true;
        } else
            return loanApplicationRepository.countByIpAddress(loanApplicationDto.getIpAddress()) >= Constants.MAX_NUMBER_OF_ATTEMPTS;
    }
}
