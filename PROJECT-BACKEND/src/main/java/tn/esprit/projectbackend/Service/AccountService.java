package tn.esprit.projectbackend.Service;


import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Account;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Repository.AccountRepository;
import tn.esprit.projectbackend.Repository.AppUserRepos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AppUserRepos appUserRepos;

    public AccountService(AccountRepository accountRepository, AppUserRepos appUserRepos) {
        this.accountRepository = accountRepository;
        this.appUserRepos = appUserRepos;
    }

    // Create Account with association to AppUser
    public Account createAccount(Long appUserId, Account account) {
        Optional<AppUser> appUserOptional = appUserRepos.findById(appUserId);
        if (appUserOptional.isPresent()) {
            account.setAppUser(appUserOptional.get());
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("AppUser not found");
        }
    }


    // Read Account by ID
    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    // Read All Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Update Account
    public Account updateAccount(UUID id, Account updatedAccount) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setUsername(updatedAccount.getUsername());
                    account.setEmail(updatedAccount.getEmail());
                    account.setPassword(updatedAccount.getPassword());
                    account.setStatus(updatedAccount.getStatus());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Delete Account
    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}
