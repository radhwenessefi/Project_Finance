package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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
    Long idPortfolio;
    @JsonProperty("Unnamed: 0")
    private int unnamedField;
    @JsonProperty("Date")
    Date date;
    @JsonProperty("Open")
    Float open;
    @JsonProperty("High")
    Float high;
    @JsonProperty("Low")
    Float low;
    @JsonProperty("Close")
    Float close;
    @JsonProperty("Adj Close")
    Float adjClose;
    @JsonProperty("Volume")
    String volume;
    @JsonProperty("Symbol")
    String symbol;
    @JsonProperty("Cluster_Labels")
    Long clusterLabels;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfolios")
    @JsonIgnore
    private Set<PortfolioInvestment> portfolioInvestments;
}
