package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Account;

import java.util.List;

public interface IAccountService {
    public Account addAccount(Account account) ;
    public void deleteAccount(long accountId) ;
    public Account updateAccount(Account account) ;
    public List<Account> getAllAccounts() ;
    public Account getAccountById(long accountId) ;




    }
