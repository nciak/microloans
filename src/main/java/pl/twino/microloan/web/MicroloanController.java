package pl.twino.microloan.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.twino.microloan.dto.LoanApplicationDto;
import pl.twino.microloan.exception.LoanDoesNotExistException;
import pl.twino.microloan.service.MicroloanService;


@RestController
@RequestMapping("/microloans")
@Slf4j
public class MicroloanController {

    private final MicroloanService microloanService;

    @Autowired
    public MicroloanController(MicroloanService microloanService) {
        this.microloanService = microloanService;
    }

    @PostMapping()
    public void createNewApplication(@RequestBody LoanApplicationDto loanApplicationDto) {
        microloanService.processApplication(loanApplicationDto);
        log.info("Application processed.", loanApplicationDto);
    }

    @PostMapping("/loandelay/{id}")
    public void delayLoan(@PathVariable("id") Long id) throws LoanDoesNotExistException {
        microloanService.delayLoan(id);
    }
}
