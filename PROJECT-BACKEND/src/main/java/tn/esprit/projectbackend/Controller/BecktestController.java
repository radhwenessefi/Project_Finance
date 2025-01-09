package tn.esprit.projectbackend.Controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projectbackend.Service.BackTestFlask;

import java.io.IOException;

@RestController
@AllArgsConstructor

public class BecktestController {


    private BackTestFlask backTestFlask;

    @GetMapping("/get_stat")
    public String callFlaskApi(@RequestParam int cash,@RequestParam float margin,@RequestParam int stratNum) throws IOException {
        System.out.println("aaaaaaaaaaaaaa"+cash+margin+stratNum);
        return backTestFlask.callFlaskApi("IBM", cash, margin,stratNum);
    }
}
