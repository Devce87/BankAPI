package backend.bankaccount.web.app.domain.dto;


import backend.bankaccount.web.app.domain.entity.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    private String name;
    private Integer age;
    private String identification;
    private Address address;
    private String phoneNumber;
    private String username;
    private String password;
}