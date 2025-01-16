package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "refund")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private double refundAmount;

    private LocalDateTime refundDate;

    private String description;

    public Refund() {}

    public Refund(Account account, double refundAmount, LocalDateTime refundDate, String description) {
        this.account = account;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
        this.description = description;
    }

}
