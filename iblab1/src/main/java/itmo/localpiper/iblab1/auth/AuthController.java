package itmo.localpiper.iblab1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itmo.localpiper.iblab1.dao.User;
import itmo.localpiper.iblab1.dto.AuthRequest;
import itmo.localpiper.iblab1.repo.UserRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository repo;


    @PostMapping("/register")
    public User register(@Valid @RequestBody AuthRequest request) {
        User user = new User(null, request.getLogin(), request.getPassword());
        return repo.save(user);
    }

    
    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthRequest request) {
        return repo.findByLoginAndPassword(request.getLogin(), request.getPassword())
        .map(u -> "Login successful for " + u.getLogin())
        .orElse("Invalid credentials");
    }
    
}
