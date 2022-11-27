package backend.bankaccount.web.app.domain.dto;


import backend.bankaccount.web.app.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotBlank
    private String identification;

    private Address address;

    @NotBlank
    @Size(min=10, max = 10, message = "Input a 10 digits phone number")
    private String phoneNumber;

    @NotBlank
    @Size(min = 8, max=30, message = "username should have at least 7 characters")
    private String username;

    @NotBlank
    @Size(min = 8,  message = "password should have at least 8 characters")
    private String password;
}