package backend.bankaccount.web.app.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@NoArgsConstructor
public class Client extends Person{

    private String username;

    private String password;

    private String status;

    @OneToMany(mappedBy = "client")
    private List<Account> accountList;


}
