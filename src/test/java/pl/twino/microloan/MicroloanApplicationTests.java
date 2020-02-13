package pl.twino.microloan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.twino.microloan.dto.LoanApplicationDto;
import pl.twino.microloan.model.Customer;
import pl.twino.microloan.model.LoanApplication;
import pl.twino.microloan.model.LoanGranted;
import pl.twino.microloan.repository.CustomerRepository;
import pl.twino.microloan.repository.LoanApplicationRepository;
import pl.twino.microloan.repository.LoanGrantedRepository;
import pl.twino.microloan.service.impl.MicroloanServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class MicroloanApplicationTests {

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Captor
    ArgumentCaptor<LoanApplication> loanApplicationArgumentCaptor;

    @Captor
    ArgumentCaptor<LoanGranted> loanGrantedArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;


    @InjectMocks
    MicroloanServiceImpl microloanService;

    @Mock
    LoanApplicationRepository loanApplicationRepository;

    @Mock
    LoanGrantedRepository loanGrantedRepository;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void testIsApplicationSaved() {
        List<LoanGranted> loanGrantedList = new ArrayList<>();
        Customer customer = prepareCustomer(loanGrantedList);
        LoanApplication loanApplication = prepareLoanApplication();
        LoanGranted loanGranted = preapreLoanGranted(customer, loanApplication);
        loanGrantedList.add(loanGranted);
        LoanApplicationDto loanApplicationDto = prepareLoanApplicationDto();

        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(loanApplication);

        microloanService.processApplication(loanApplicationDto);

        verify(loanApplicationRepository).save(loanApplicationArgumentCaptor.capture());

        assertThat(loanApplicationArgumentCaptor.getValue().getAmount()).isEqualTo(2000);
    }


    @Test
    public void testDelayLoan() {
        List<LoanGranted> loanGrantedList = new ArrayList<>();
        Customer customer = prepareCustomer(loanGrantedList);

        LoanApplication loanApplication = prepareLoanApplication();
        LocalDate firstLocalDate = loanApplication.getEndDateTerm();

        LoanGranted loanGranted = preapreLoanGranted(customer, loanApplication);
        loanGrantedList.add(loanGranted);

        when(loanGrantedRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(loanGranted));

        microloanService.delayLoan(1L, 1L);

        verify(loanApplicationRepository).save(loanApplicationArgumentCaptor.capture());

        assertThat(loanApplicationArgumentCaptor.getValue()).isNotNull();
        assertThat(loanApplicationArgumentCaptor.getValue().getEndDateTerm()).isEqualTo(firstLocalDate.plusDays(14));
    }


    public void testVerifyRequiredFields() {
    }

    public void testAddLoanGrantedForCustomer() {

    }

    @Test
    public void testCreateNewCustomer() {
        List<LoanGranted> loanGrantedList = new ArrayList<>();
        Customer customer = prepareCustomer(loanGrantedList);
        LoanApplication loanApplication = prepareLoanApplication();
        LoanGranted loanGranted = preapreLoanGranted(customer, loanApplication);
        loanGrantedList.add(loanGranted);
        LoanApplicationDto loanApplicationDto = prepareLoanApplicationDto();

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        microloanService.processApplication(loanApplicationDto);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getAllValues()).size().isEqualTo(1);
    }

    public void testConvertToLoanGranted() {

    }

    public void testCreateApplicationToSaveInDb() {

    }

    public void testIsRiskyApplication() {

    }

    private LoanApplicationDto prepareLoanApplicationDto() {
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setIpAddress("123.123.123.123");
        loanApplicationDto.setDays(20);
        loanApplicationDto.setDate(LocalDateTime.of(2020, 2, 10, 12, 0, 0));
        loanApplicationDto.setAmount(2000);
        return loanApplicationDto;
    }

    private LoanGranted preapreLoanGranted(Customer customer, LoanApplication loanApplication) {
        LoanGranted loanGranted = new LoanGranted();
        loanGranted.setId(1L);
        loanGranted.setCustomer(customer);
        loanGranted.setDelayed(false);
        loanGranted.setLoanApplication(loanApplication);
        return loanGranted;
    }

    private LoanApplication prepareLoanApplication() {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setAmount(2000);
        loanApplication.setEndDateTerm(LocalDate.of(2020, 3, 9));
        loanApplication.setIpAddress("123.123.123.123");
        return loanApplication;
    }

    private Customer prepareCustomer(List<LoanGranted> loanGrantedList) {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLoans(loanGrantedList);
        return customer;
    }
}

