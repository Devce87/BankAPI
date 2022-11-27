package backend.bankaccount.web.app.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@Where(clause = "deleted = false")
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Age can not be empty")
    private Integer age;

    @NotBlank(message = "Id can not be blank")
    private String identification;

    @Embedded
    private Address address;

    @NotBlank
    @Size(min=10, max = 10, message = "Input a 10 digits phone number")
    private String phoneNumber;

    private boolean deleted = Boolean.FALSE;


}
