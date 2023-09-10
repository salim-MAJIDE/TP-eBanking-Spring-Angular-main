package ma.enset.ebankingbackend1.dtos;

import lombok.Data;
import ma.enset.ebankingbackend1.enums.AccountStatus;
import java.util.Date;
@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createDate;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
