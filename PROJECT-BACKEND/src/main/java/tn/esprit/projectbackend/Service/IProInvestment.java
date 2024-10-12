package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.PortfolioInvestment;


import java.util.List;

public interface IProInvestment {
    public List<PortfolioInvestment> getAllInvestment();
    public void addPortfolioInvestment(PortfolioInvestment p,Long userId,Long portfolioId);
    public void closeOrder(Long invId);
    public PortfolioInvestment updateOrder (PortfolioInvestment portfolioInvestment);
}
