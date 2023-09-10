package ma.enset.ebankingbackend1.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankingbackend1.entities.BankAccount;

import java.util.List;


@Data
@AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
