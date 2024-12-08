package tn.esprit.projectbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.projectbackend.Entity.User;
import tn.esprit.projectbackend.Repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImp implements IUserService{
    UserRepository userRepository;
    public User addUser(User u){
        return userRepository.save(u);
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }


}
