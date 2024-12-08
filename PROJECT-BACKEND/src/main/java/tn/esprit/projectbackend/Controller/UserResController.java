package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projectbackend.Entity.User;
import tn.esprit.projectbackend.Service.UserServiceImp;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserResController {
    UserServiceImp userService;
    @PostMapping("/add-user")
    public User addUser(@RequestBody User u){
        User user = userService.addUser(u);
        return  user;
    }

}
