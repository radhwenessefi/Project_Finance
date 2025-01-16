package tn.esprit.projectbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Account;
import tn.esprit.projectbackend.Entity.Insurance;
import tn.esprit.projectbackend.Entity.Pack;
import tn.esprit.projectbackend.Repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(long accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

//    public Account addInsuranceToAccount(UUID accountId, Insurance insurance) {
//        Optional<Account> optionalAccount = accountRepository.findById(accountId);
//
//        if (optionalAccount.isPresent()) {
//            Account account = optionalAccount.get();
//            Set<Insurance> insurances = account.getInsurances();
//            insurances.add(insurance);
//            account.setInsurances(insurances);
//            return accountRepository.save(account);
//        } else {
//            throw new RuntimeException("Account not found");
//        }
//    }
//
//    public Account setPackToAccount(UUID accountId, Pack pack) {
//        Optional<Account> optionalAccount = accountRepository.findById(accountId);
//
//        if (optionalAccount.isPresent()) {
//            Account account = optionalAccount.get();
//            account.setPack(pack);
//            return accountRepository.save(account);
//        } else {
//            throw new RuntimeException("Account not found");
//        }
//    }
}
