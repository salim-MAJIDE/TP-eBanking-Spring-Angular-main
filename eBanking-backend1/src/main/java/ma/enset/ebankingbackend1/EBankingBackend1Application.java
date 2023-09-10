package ma.enset.ebankingbackend1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import ma.enset.ebankingbackend1.dtos.BankAccountDTO;
import ma.enset.ebankingbackend1.dtos.CurrentBankAccountDTO;
import ma.enset.ebankingbackend1.dtos.CustomerDTO;
import ma.enset.ebankingbackend1.dtos.SavingBankAccountDTO;
import ma.enset.ebankingbackend1.entities.*;
import ma.enset.ebankingbackend1.enums.AccountStatus;
import ma.enset.ebankingbackend1.enums.OperationType;
import ma.enset.ebankingbackend1.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend1.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend1.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend1.repositories.AccountOperationRepository;
import ma.enset.ebankingbackend1.repositories.BankAccountRepository;
import ma.enset.ebankingbackend1.repositories.CustomerRepository;
import ma.enset.ebankingbackend1.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication

public class EBankingBackend1Application {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackend1Application.class, args);
    }
  @Bean
  CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
           bankAccountService.list_customers().forEach(customerDTO->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customerDTO.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customerDTO.getId());


                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i=0;i<10;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    }
                    else {
                        accountId= ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"debit");

                }
            }

        };
  }
    //@Bean
  CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
        return args -> {
            BankAccount bankAccount=bankAccountRepository.findById("364f220d-92d6-47fb-bbdd-51dd57d6cb38").orElse(null);
            if(bankAccount instanceof CurrentAccount){
                System.out.print("Over Draft : "+((CurrentAccount) bankAccount).getOverDraft()+"\t");
                System.out.print("Balance : "+bankAccount.getBalance()+"\t");
                System.out.print("Status : "+bankAccount.getStatus()+"\t");
                System.out.print("Date : "+bankAccount.getCreateDate()+"\t");
            } else if (bankAccount instanceof SavingAccount) {
                System.out.print("Inters Rate : "+((SavingAccount) bankAccount).getIntersRate()+"\t");
                System.out.print("Balance : "+bankAccount.getBalance()+"\t");
                System.out.print("Status : "+bankAccount.getStatus()+"\t");
                System.out.print("Date : "+bankAccount.getCreateDate()+"\t");
            }
        };
  }
   // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                //current Account
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreateDate(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                //saving account
                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreateDate(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setIntersRate(5.5);
                bankAccountRepository.save(currentAccount);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i=0;i<5;i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*120000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };
    }
}
