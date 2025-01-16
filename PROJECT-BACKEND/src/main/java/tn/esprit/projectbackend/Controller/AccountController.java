package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Account;
import tn.esprit.projectbackend.Entity.Insurance;
import tn.esprit.projectbackend.Repository.AccountRepository;
import tn.esprit.projectbackend.Service.IAccountService;
import tn.esprit.projectbackend.Service.IInsuranceService;
import tn.esprit.projectbackend.Service.IPackService;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/Account")
public class AccountController {

    private IInsuranceService iInsuranceService;
    private IPackService iPackService;
    private IAccountService iAccountService;
    private AccountRepository accountRepository;

    @PostMapping("/add")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        Account savedAccount = iAccountService.addAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") long id) {
        iAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account updatedAccount = iAccountService.updateAccount(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/all")
    public List<Account> getAllAccount() {
        return iAccountService.getAllAccounts();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") long id) {
        Account account = iAccountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

}
