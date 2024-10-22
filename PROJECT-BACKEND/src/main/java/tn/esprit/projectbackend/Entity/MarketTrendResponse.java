package tn.esprit.projectbackend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MarketTrendResponse {
    private List<Actions> topPerformers;
    private List<Actions> worstPerformers;

    public MarketTrendResponse(List<Actions> topPerformers, List<Actions> worstPerformers) {
        this.topPerformers = topPerformers;
        this.worstPerformers = worstPerformers;
    }


}
