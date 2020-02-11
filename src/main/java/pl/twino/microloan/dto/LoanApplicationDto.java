package pl.twino.microloan.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanApplicationDto {

    private Long customerId;

    private double amount;

    private long days;

    private LocalDateTime date;

    private String ipAddress;

}
