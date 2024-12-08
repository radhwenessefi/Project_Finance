package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPortfolio;

    @JsonProperty("Ticker")
    private String symbol;

    @JsonProperty("Open")
    private Float open;

    @JsonProperty("High")
    private Float high;

    @JsonProperty("Low")
    private Float low;

    @JsonProperty("Close")
    private Float close;

    @JsonProperty("Adj Close")
    private Float adjClose;

    @JsonProperty("Volume")
    private BigDecimal volume;

    @JsonProperty("Cluster_Labels")
    private Long clusterLabels;

    @ManyToMany(mappedBy = "portfolios")
    @JsonIgnore
    @ToString.Exclude // Exclude the collection to prevent circular references
    private Set<PortfolioInvestment> portfolioInvestments;
}
