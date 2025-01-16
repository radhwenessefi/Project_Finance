package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Account;

import java.util.List;

public interface IRiskScoreService {
    public double calculateStandardDeviation(List<Double> values) ;
    public String calculateRiskScore(Account account) ;


    }
