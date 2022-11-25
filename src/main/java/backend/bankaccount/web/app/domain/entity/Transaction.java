package backend.bankaccount.web.app.domain.entity;

import backend.bankaccount.web.app.constants.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@SQLDelete(sql = "UPDATE transactions SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private TransactionType transactionType;

    private Double transactionAmount;

    private Double accountBalance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private boolean deleted = Boolean.FALSE;
}