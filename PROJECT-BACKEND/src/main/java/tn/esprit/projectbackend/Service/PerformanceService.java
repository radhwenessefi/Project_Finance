package tn.esprit.projectbackend.Service;

import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Repository.AccountRepository;
import tn.esprit.projectbackend.Repository.PortfolioRepository;

@Service
public class PerformanceService implements IPerformanceService{
    AccountRepository accountRepository;
    PortfolioRepository portfolioRepository;

}
