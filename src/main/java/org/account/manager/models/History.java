package org.account.manager.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Account.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_id")
    private Account account;
    @Column
    private Float amount;
    @Column
    @Enumerated(EnumType.STRING)
    private Operation operation;
    @Column
    private LocalDateTime dateTime;
    @Column
    private Float previousBalance;
    @Column
    private Float newBalance;

    public History(Account account, Float amount, Float previousBalance, Float newBalance, Operation operation) {
        this.account = account;
        this.amount = amount;
        this.dateTime = now();
        this.operation = operation;
        this.previousBalance = previousBalance;
        this.newBalance = newBalance;
    }
}
