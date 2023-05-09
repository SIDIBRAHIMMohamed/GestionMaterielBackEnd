package com.esp.irt.backend.services;

import com.esp.irt.backend.entities.Role;
import com.esp.irt.backend.entities.User;
import com.esp.irt.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUser(User user) throws Exception {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isEmpty()) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            if (user.getRole() == null)
                { user.setRole(Role.USER);}
            userRepository.save(user);
        }
        else throw new Exception("ce user existe deja");

    }

    public void updateUser(long id, User user) {
        Optional userData = userRepository.findById(id);
        if (userData.isPresent()){
            User _user = (User) userData.orElseThrow();
            _user.setEmail(user.getEmail());
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            _user.setPassword(encoder.encode(user.getPassword()));
            _user.setRole(user.getRole());
            userRepository.save(_user);
        }
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return user.get();
    }
}