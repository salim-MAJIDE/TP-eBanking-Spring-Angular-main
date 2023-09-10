package ma.enset.ebankingbackend1.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend1.dtos.CustomerDTO;
import ma.enset.ebankingbackend1.entities.Customer;
import ma.enset.ebankingbackend1.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend1.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('SCOPE_USER')")
    public List<CustomerDTO> customers(){
        return bankAccountService.list_customers();
    }
    @GetMapping("/customers/search")
    @PreAuthorize("hasAnyRole('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers(keyword);
    }
    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAnyRole('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    @PreAuthorize("hasAnyRole('SCOPE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
       return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAnyRole('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable(name = "id")Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAnyRole('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable(name = "id") Long id){
        bankAccountService.deleteCustomer(id);
    }
}
