package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PortfolioInvestment {
    @Valid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPortfolioInvestement;

    @NotNull(message = "Amount can't be null")
    private Long amount;

    @NotNull(message = "Date of inscription can't be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfInsecription;

    @NotNull(message = "Take profit can't be null")
    private Long takeProfit;

    @NotNull(message = "Stop loss can't be null")
    private Long stopLoss;

    @NotNull(message = "Order type can't be null")
    @Enumerated(EnumType.STRING)

    private OrderType orderType;

    private Long clusterLabels;
    @ManyToMany
    @JoinTable(
            name = "portfolio_investment_portfolio",
            joinColumns = @JoinColumn(name = "portfolio_investment_id"),
            inverseJoinColumns = @JoinColumn(name = "portfolio_id")
    )
    @JsonIgnore
    @ToString.Exclude // Exclude the collection to prevent circular references
    private Set<Portfolio> portfolios;

    @ManyToOne
    @JsonIgnore
    private User usersportfolio;
}
