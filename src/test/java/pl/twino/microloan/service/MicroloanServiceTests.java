package pl.twino.microloan.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.twino.microloan.dto.LoanApplicationDto;
import pl.twino.microloan.model.Customer;
import pl.twino.microloan.model.LoanGranted;
import pl.twino.microloan.repository.CustomerRepository;
import pl.twino.microloan.service.impl.MicroloanServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@RunWith(MockitoJUnitRunner.class)
public class MicroloanServiceTests {

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;
    @Captor
    ArgumentCaptor<LoanApplicationDto> dtoArgumentCaptor;

    @InjectMocks
    private MicroloanServiceImpl microloanServiceImpl;

    @Mock
    private CustomerRepository customerRepository;

    private LoanApplicationDto noRiskLoanApplicationDtoNewCustomer;


    @Before
    public void setUp() {
        noRiskLoanApplicationDtoNewCustomer = prepareNoRiskLoanApplicationDtoNewCustomer();
    }



    @Test
    public void createNewCustomer() {

        microloanServiceImpl.processApplication(noRiskLoanApplicationDtoNewCustomer);
        //TODO
    }

    private LoanApplicationDto prepareNoRiskLoanApplicationDtoNewCustomer() {
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setAmount(2000);
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        loanApplicationDto.setDate(specificDate);
        loanApplicationDto.setDays(20);
        loanApplicationDto.setIpAddress("123.123.123.123");
        return loanApplicationDto;
    }
}
