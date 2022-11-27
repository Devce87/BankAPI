package backend.bankaccount.web.app.domain.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE person SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Person{

    @NotBlank
    @Size(min = 8, max=30, message = "username should have at least 7 characters")
    private String username;

    @NotBlank
    @Size(min = 8,  message = "password should have at least 8 characters")
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Account> accountList;

    private boolean deleted = Boolean.FALSE;


}
